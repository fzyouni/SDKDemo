package com.sensorsdata.demo.util;

import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.analytics.javasdk.bean.ItemRecord;
import com.sensorsdata.analytics.javasdk.bean.UserRecord;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 结合spring 容器来构建一个线程池工具类，将日志track操作交给子线程来完成，不影响主线程的处理耗时。
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/07/30 17:17
 */
@Slf4j
public class SensorsLogUtil {

  private SensorsLogUtil() {
  }
  /**
   * 根据业务服务器实际性能决定开启线程数
   */
  private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

  /**
   * event 事件
   *
   * @param sa          神策分析实例对象
   * @param eventRecord 事件记录对象
   */
  public static void event(ISensorsAnalytics sa, EventRecord eventRecord) {
    executorService.execute(new DealTask(sa, eventRecord));
  }

  /**
   * user 信息
   *
   * @param sa         神策分析实例对象
   * @param userRecord 用户记录对象
   */
  public static void user(ISensorsAnalytics sa, UserRecord userRecord) {
    executorService.execute(new DealTask(sa, userRecord));
  }

  /**
   * item 信息
   *
   * @param sa         神策分析实例对象
   * @param itemRecord 维度记录对象
   */
  public static void item(ISensorsAnalytics sa, ItemRecord itemRecord) {
    executorService.execute(new DealTask(sa, itemRecord));
  }

  public static void shutdown() {
    log.info("start to shutdown the thread pool.");
    executorService.shutdown();
  }

  static class DealTask implements Runnable {

    private final ISensorsAnalytics sa;
    private final Integer type;
    private EventRecord eventRecord;
    private UserRecord userRecord;
    private ItemRecord itemRecord;


    DealTask(ISensorsAnalytics sa, EventRecord eventRecord) {
      this.eventRecord = eventRecord;
      this.sa = sa;
      this.type = 1;
    }

    DealTask(ISensorsAnalytics sa, UserRecord userRecord) {
      this.userRecord = userRecord;
      this.sa = sa;
      this.type = 2;
    }

    DealTask(ISensorsAnalytics sa, ItemRecord itemRecord) {
      this.itemRecord = itemRecord;
      this.sa = sa;
      this.type = 3;
    }

    @Override
    public void run() {
      try {
        switch (type) {
          case 1:
            sa.track(eventRecord);
            break;
          case 2:
            sa.profileSet(userRecord);
            break;
          case 3:
            sa.itemSet(itemRecord);
            break;
        }
      } catch (InvalidArgumentException e) {
        log.error("log write to sensors fail.{}", e.getMessage());
      }
    }
  }

}
