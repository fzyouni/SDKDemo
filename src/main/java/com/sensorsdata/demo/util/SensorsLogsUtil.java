package com.sensorsdata.demo.util;

import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.analytics.javasdk.bean.ItemRecord;
import com.sensorsdata.analytics.javasdk.bean.UserRecord;
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer;
import com.sensorsdata.analytics.javasdk.consumer.ConcurrentLoggingConsumer;
import com.sensorsdata.analytics.javasdk.consumer.DebugConsumer;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 开启线程池模式，调用者将日志信息实体扔到线程池任务队列中，由子线程去处理
 * 将 sa 实例操作放到工具类内部，外部调用更加简单
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/07/31 10:28
 */
public class SensorsLogsUtil {

  private SensorsLogsUtil() {
  }

  private final static Logger logger = LoggerFactory.getLogger(SensorsLogsUtil.class);

  /**
   * 根据业务服务器实际性能决定开启线程数
   */
  private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

  /**
   * 构建 sa 实例化对象
   */
  private static ISensorsAnalytics sa;

  static {
    try {
      //在这里配置神策 SDK 初始化操作
      //sa = new SensorsAnalytics(new ConcurrentLoggingConsumer("file.log"));
      sa = new SensorsAnalytics(new BatchConsumer("http://10.129.138.189:8106/sa?project=production"));
    } catch (Exception e) {
      logger.error("initialize sensorsAnalytics fail.{}", e.getMessage());
    }
  }

  public static void event(EventRecord eventRecord) {
    executorService.execute(new DealTask(eventRecord));
  }

  public static void user(UserRecord userRecord) {
    executorService.execute(new DealTask(userRecord));
  }

  public static void item(ItemRecord itemRecord) {
    executorService.execute(new DealTask(itemRecord));
  }

  /**
   * 非程序结束，不要调用此方法
   */
  public static void shutdown() {
    logger.info("start to shutdown the thread pool.");
    executorService.shutdown();
    try {
      //等待未完成的任务结束
      if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        //取消当前执行的任务
        executorService.shutdownNow();
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
          logger.error("Thread pool can't be shutdown even with interrupting worker threads.");
        }
      }
    } catch (InterruptedException e) {
      // 重新取消当前线程进行中断
      executorService.shutdownNow();
      logger.error("The current server thread is interrupted when it is trying to stop the worker threads. ");
      // 保留中断状态
      Thread.currentThread().interrupt();
    }
    sa.shutdown();
  }


  static class DealTask implements Runnable {

    private final Integer type;
    private EventRecord eventRecord;
    private UserRecord userRecord;
    private ItemRecord itemRecord;


    DealTask(EventRecord eventRecord) {
      this.eventRecord = eventRecord;
      this.type = 1;
    }

    DealTask(UserRecord userRecord) {
      this.userRecord = userRecord;
      this.type = 2;
    }

    DealTask(ItemRecord itemRecord) {
      this.itemRecord = itemRecord;
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
        e.printStackTrace();
      }
    }
  }
}
