package com.sensorsdata.demo.controller;

import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import com.sensorsdata.demo.service.ITestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 前端视图控制器
 *
 * @author fz <fangzhuo@sensorsdata.cn>
 * @version 1.0.0
 * @since 2021/06/22 11:22
 */
@RestController
public class TestController {

  /**
   * 业务逻辑实现接口
   */
  @Autowired
  ITestService testService;

  /**
   * 用户匿名访问网站
   *
   * @return success/error
   */
  @GetMapping("visitHome")
  public String visitHome(String cookieId) throws InvalidArgumentException, JsonProcessingException {
    return testService.visitHome(cookieId);
  }

  /**
   * 用户匿名搜索网站
   *
   * @param cookieId 匿名Id
   * @return success/error
   * @throws InvalidArgumentException
   */
  @GetMapping("searchGoods")
  public String searchGoods(String cookieId) throws InvalidArgumentException, JsonProcessingException {
    return testService.searchGoods(cookieId);
  }

  /**
   * 用户登陆事件记录
   *
   * @param cookieId 匿名ID
   * @param userId   登录ID
   * @return
   * @throws InvalidArgumentException
   */
  @GetMapping("signup")
  public String signup(String cookieId, String userId) throws InvalidArgumentException {
    return testService.signup(cookieId, userId);
  }

  /**
   * 用户信息补充
   *
   * @param userId       用户id
   * @param name         姓名
   * @param age          年龄
   * @param gender       性别
   * @param registerDate 注册时间
   * @return
   */
  @GetMapping("userInfo")
  public String userInfo(String userId, String name, Integer age, String gender, String registerDate)
      throws InvalidArgumentException, JsonProcessingException {
    return testService.userInfo(userId, name, age, gender, registerDate);
  }

  /**
   * 维度表记录
   *
   * @param params 参数
   * @return
   * @throws InvalidArgumentException
   * @throws JsonProcessingException
   */
  @GetMapping("itemInfo")
  public String itemInfo(@RequestParam Map<String, Object> params)
      throws InvalidArgumentException, JsonProcessingException {
    return testService.itemInfo(params);
  }

  /**
   * ab 试验 （该接口只支持接收默认值为 String 类型）
   *
   * @param userId                 用户ID
   * @param experimentVariableName 试验变量名
   * @param defaultValue           默认值
   * @return
   * @throws InvalidArgumentException
   * @throws IOException
   */
  @GetMapping("abTest")
  public String abTest(String userId, String experimentVariableName, String defaultValue)
      throws InvalidArgumentException, IOException {
    return testService.abTest(userId, experimentVariableName, defaultValue);
  }

  @GetMapping("asyncFetchABTest")
  public String asyncFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException {
    return testService.asyncFetchABTest(userId, experimentVariableName, defaultValue);
  }

  @GetMapping("fastFetchABTest")
  public String fastFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException {
    return testService.fastFetchABTest(userId, experimentVariableName, defaultValue);
  }

  @GetMapping("flush")
  public void flush() {
    testService.flush();
  }

  @PostMapping("testNoRequest")
  public String testNoRequest() {
    return "{\n"
        + "    \"status\": \"SUCCESS\",\n"
        + "    \"results\": [\n"
        + "        {\n"
        + "            \"abtest_experiment_id\": \"22\",\n"
        + "            \"abtest_experiment_group_id\": \"1\",\n"
        + "            \"is_control_group\": false,\n"
        + "            \"is_white_list\": false,\n"
        + "            \"experiment_type\": \"CODE\",\n"
        + "            \"variables\": [\n"
        + "                {\n"
        + "                    \"name\": \"test_xl\",\n"
        + "                    \"type\": \"STRING\",\n"
        + "                    \"value\": \"试验组1\"\n"
        + "                }\n"
        + "            ]\n"
        + "        },\n"
        + "        {\n"
        + "            \"abtest_experiment_id\": \"23\",\n"
        + "            \"abtest_experiment_group_id\": \"1\",\n"
        + "            \"is_control_group\": false,\n"
        + "            \"is_white_list\": false,\n"
        + "            \"experiment_type\": \"CODE\",\n"
        + "            \"variables\": [\n"
        + "                {\n"
        + "                    \"name\": \"test_xl\",\n"
        + "                    \"type\": \"STRING\",\n"
        + "                    \"value\": \"试验组1\"\n"
        + "                }\n"
        + "            ]\n"
        + "        },\n"
        + "        {\n"
        + "            \"abtest_experiment_id\": \"24\",\n"
        + "            \"abtest_experiment_group_id\": \"0\",\n"
        + "            \"is_control_group\": true,\n"
        + "            \"is_white_list\": false,\n"
        + "            \"experiment_type\": \"CODE\",\n"
        + "            \"variables\": [\n"
        + "                {\n"
        + "                    \"name\": \"ceshi\",\n"
        + "                    \"type\": \"STRING\",\n"
        + "                    \"value\": \"ceshi1\"\n"
        + "                }\n"
        + "            ]\n"
        + "        },\n"
        + "        {\n"
        + "            \"abtest_experiment_id\": \"25\",\n"
        + "            \"abtest_experiment_group_id\": \"0\",\n"
        + "            \"is_control_group\": true,\n"
        + "            \"is_white_list\": false,\n"
        + "            \"experiment_type\": \"CODE\",\n"
        + "            \"variables\": [\n"
        + "                {\n"
        + "                    \"name\": \"cs\",\n"
        + "                    \"type\": \"INTEGER\",\n"
        + "                    \"value\": \"1\"\n"
        + "                }\n"
        + "            ]\n"
        + "        },\n"
        + "        {\n"
        + "            \"abtest_experiment_id\": \"29\",\n"
        + "            \"abtest_experiment_group_id\": \"0\",\n"
        + "            \"is_control_group\": true,\n"
        + "            \"is_white_list\": false,\n"
        + "            \"experiment_type\": \"CODE\",\n"
        + "            \"variables\": [\n"
        + "                {\n"
        + "                    \"name\": \"testname\",\n"
        + "                    \"type\": \"INTEGER\",\n"
        + "                    \"value\": \"11\"\n"
        + "                }\n"
        + "            ]\n"
        + "        }\n"
        + "    ]\n"
        + "}";
  }


  @GetMapping("hello")
  public String hello(String userId) throws InvalidArgumentException {
    return testService.hello(userId);
  }

  @GetMapping("asycLog")
  public void asycLog() throws InvalidArgumentException {
    testService.asycLog();
  }

  @GetMapping("syncUserLog")
  public Long syncUserLog() throws InvalidArgumentException {
    return testService.syncUserLog();
  }



  @GetMapping("generateLog")
  public String generateLog(Integer integer) throws InvalidArgumentException {
    return testService.generateLog(integer);
  }


}
