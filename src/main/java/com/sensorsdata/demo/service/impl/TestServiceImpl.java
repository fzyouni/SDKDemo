package com.sensorsdata.demo.service.impl;

import com.sensorsdata.analytics.javasdk.ISensorsABTest;
import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.analytics.javasdk.bean.Experiment;
import com.sensorsdata.analytics.javasdk.bean.ItemRecord;
import com.sensorsdata.analytics.javasdk.bean.UserRecord;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import com.sensorsdata.analytics.javasdk.util.SensorsAnalyticsUtil;
import com.sensorsdata.demo.service.ITestService;
import com.sensorsdata.demo.util.SensorsLogUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fz <fangzhuo@sensorsdata.cn>
 * @version 1.0.0
 * @since 2021/06/22 11:17
 */
@Service
@Slf4j
public class TestServiceImpl implements ITestService {

  /**
   * 注入神策 abTest 对象
   */
  @Autowired
  ISensorsABTest abTest;

  //注入神策实例化对象
  @Autowired
  ISensorsAnalytics sa;

  private ObjectMapper mapper = SensorsAnalyticsUtil.getJsonObjectMapper();

  @Override
  public String visitHome(String cookieId) throws InvalidArgumentException, JsonProcessingException {
    EventRecord firstRecord = EventRecord.builder().setDistinctId(cookieId).isLoginId(Boolean.FALSE)
        .setEventName("track")
        .addProperty("$time", Calendar.getInstance().getTime())
        .addProperty("Channel", "baidu")
        .build();
    sa.track(firstRecord);
    sa.flush();
    return String.format("用户访问网站记录已生成：%s", SensorsAnalyticsUtil.getJsonObjectMapper().writeValueAsString(firstRecord));
  }

  @Override
  public String searchGoods(String cookieId) throws InvalidArgumentException, JsonProcessingException {
    EventRecord searchRecord = EventRecord.builder().setDistinctId(cookieId).isLoginId(Boolean.FALSE)
        .setEventName("SearchProduct")
        .addProperty("KeyWord", "XX手机")
        .build();
    sa.track(searchRecord);
    return String.format("用户搜索商品记录已生成：%s", SensorsAnalyticsUtil.getJsonObjectMapper().writeValueAsString(searchRecord));
  }

  @Override
  public String signup(String cookieId, String userId) throws InvalidArgumentException {
    sa.trackSignUp(userId, cookieId);
    sa.flush();
    return String.format("用户登陆信息已生成：用户登陆id:%s,匿名id:%s", userId, cookieId);
  }

  @Override
  public String userInfo(String userId, String name, Integer age, String gender, String registerDate)
      throws InvalidArgumentException, JsonProcessingException {
    UserRecord userRecord = UserRecord.builder().setDistinctId(userId).isLoginId(Boolean.TRUE)
        .addProperty("$name", name)
        .addProperty("$signup_time",
            LocalDateTime.parse(registerDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochSecond(
                ZoneOffset.of("+8")))
        .addProperty("Gender", gender)
        .addProperty("age", age)
        .build();
    sa.profileSet(userRecord);
    return String.format("用户注册信息已生成：%s", SensorsAnalyticsUtil.getJsonObjectMapper().writeValueAsString(userRecord));
  }

  @Override
  public String itemInfo(Map<String, Object> params)
      throws InvalidArgumentException, JsonProcessingException {
    String itemId = params.getOrDefault("itemId", "111").toString();
    params.remove("itemId");
    String itemType = params.getOrDefault("itemType", "type").toString();
    params.remove("itemType");
    ItemRecord addRecord = ItemRecord.builder().setItemId(itemId)
        .setItemType(itemType)
        .addProperties(params)
        .build();
    sa.itemSet(addRecord);
    return String.format("维度信息已生成：%s", SensorsAnalyticsUtil.getJsonObjectMapper().writeValueAsString(addRecord));
  }

  @Override
  public String hello(String userId) throws InvalidArgumentException {
    /*final EventRecord.Builder test = EventRecord.builder().setDistinctId("fz-1").setEventName("test").isLoginId(true);
    if (Objects.nonNull(userId)) {
      test.addProperty("haha", userId);
    }
    EventRecord build = test.build();*/
    Long start = System.currentTimeMillis();
    doCouponTakeReport();
    return String.format("执行消耗时间为：%d", System.currentTimeMillis() - start);
  }

  @Override
  public void asycLog() throws InvalidArgumentException {
    doCouponTakeReport();
  }

  @Async
  public void doCouponTakeReport() {
    List<TransLog> transLog = searchLogList();
    transLog.parallelStream().forEach(this::createReportByParam);
  }

  private List<TransLog> searchLogList() {
    List<TransLog> res = new ArrayList<>(10000);
    for (int i = 0; i < 10000; i++) {
      res.add(new TransLog("test-" + i, true));
    }
    return res;
  }

  private void createReportByParam(TransLog transLog) {
    Map<String, Object> postMap = new HashMap<String, Object>(16);
    postMap.put("generateTime", System.currentTimeMillis());
    try {
      sa.track(transLog.distinctId, transLog.getIsLoginId(), "coupon_collection", postMap);
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
    }
  }

  @Data
  @AllArgsConstructor
  static class TransLog {
    private String distinctId;
    private Boolean isLoginId;
  }


  @Async
  public void generateEventLog(String userId, String eventName) {
    try {
      sa.track(userId, true, eventName);
    } catch (InvalidArgumentException e) {
      log.error(e.getMessage());
    }
  }

  @Override
  public Long syncUserLog() throws InvalidArgumentException {
    Long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {
      UserRecord userRecord = UserRecord.builder().setDistinctId("zz-" + i).isLoginId(true).build();
      SensorsLogUtil.user(sa, userRecord);
    }
    return System.currentTimeMillis() - start;
  }



  @Override
  public String abTest(String userId, String experimentVariableName, String defaultValue)
      throws IOException {
    Experiment<String> experiment = abTest.asyncFetchABTest(userId, true, experimentVariableName, defaultValue);
    return String.format("获取试验结果为：%s", mapper.writeValueAsString(experiment));
  }

  @Override
  public String fastFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException {
    Experiment<String> experiment = abTest.fastFetchABTest(userId, true, experimentVariableName, defaultValue);
    return String.format("获取试验结果为：%s", mapper.writeValueAsString(experiment));
  }

  @Override
  public String asyncFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException {
    Experiment<String> experiment = abTest.asyncFetchABTest(userId, true, experimentVariableName, defaultValue);
    return String.format("获取试验结果为：%s", mapper.writeValueAsString(experiment));
  }

  @Override
  public String generateLog(Integer integer) throws InvalidArgumentException {
    /*EventRecord eventRecord = null;
    for (int i = integer; i < integer + 10; i++) {
      String id = String.format("%s-%d", "fz", i);
      eventRecord = EventRecord.builder().setDistinctId(id).isLoginId(Boolean.TRUE)
          .setEventName("track")
          .addProperty("$time", Calendar.getInstance().getTime())
          .build();
      sa.track(eventRecord);
    }
    sa.flush();
    return eventRecord.toString();*/
    return "";
  }

  @Override
  public void flush() {
    sa.flush();
  }
}
