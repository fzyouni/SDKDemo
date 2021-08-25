package com.sensorsdata.demo.test;

import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.demo.util.SensorsLogsUtil;

/**
 * 普通Java 程序使用 SensorsLogsUtil(异步) 工具类来实现日志采集和落盘
 * 这个工具类可以直接让客户拿过去直接使用
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/07/28 13:53
 */
public class SensorsDemo {

  public static void main(String[] args) throws Exception {
    //记录程序开始时间
    Long start = System.currentTimeMillis();
    //生成 20 条记录，扔到线程池里面异步处理
    for (int i = 0; i < 20; i++) {
      EventRecord eventRecord =
          EventRecord.builder().setDistinctId("fz-" + i).isLoginId(true).setEventName("test2").build();
      SensorsLogsUtil.event(eventRecord);
    }
    //打印程序执行耗时
    System.out.printf("程序总运行耗时为：%d%n", System.currentTimeMillis() - start);

    //使用完优雅关闭线程池
    Runtime.getRuntime().addShutdownHook(new Thread(SensorsLogsUtil::shutdown));
  }
}
