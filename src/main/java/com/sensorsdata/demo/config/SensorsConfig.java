package com.sensorsdata.demo.config;

import com.sensorsdata.analytics.javasdk.ISensorsABTest;
import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.SensorsABTest;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.ABGlobalConfig;
import com.sensorsdata.analytics.javasdk.consumer.ConcurrentLoggingConsumer;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 初始化神策对象，声明成一个bean
 *
 * @author fz <fangzhuo@sensorsdata.cn>
 * @version 1.0.0
 * @since 2021/06/22 11:13
 */
@Configuration
public class SensorsConfig {

  /**
   * 文件路径，配置在 resources 目录 application.yml 文件下，如果想修改，直接改该文件下对应变量值
   */
  @Value("${file.prefix}")
  private String filenamePrefix;

  @Bean(name = "sa", destroyMethod = "shutdown")
  public ISensorsAnalytics initSensorsAnalytics() throws IOException {
    //本地日志模式（此模式会在指定路径生成相应的日志文件）
    System.out.println("========= 文件名：" + filenamePrefix);
    return new SensorsAnalytics(new ConcurrentLoggingConsumer(filenamePrefix));
    //debug 模式(此模式只适用于测试集成 SDK 功能，千万不要使用到生产环境！！！)
    //return new SensorsAnalytics(new DebugConsumer("数据接收地址", true));
    //网络批量发送模式（此模式在容器关闭的时候，如果存在数据还没有发送完毕，就会丢失未发送的数据！！！）
    //return new SensorsAnalytics(new BatchConsumer("数据接收地址"));
  }

  @Bean
  public ISensorsABTest initSensorsABTest(ISensorsAnalytics sa) throws InvalidArgumentException {
    String url =
        "https://abtest-aws-us-east-01.saas.sensorsdata.com/api/v2/abtest/online/results?project-key=0675F7B571A0D5C1A82B079F8D54B7D1E28627F2";
    ABGlobalConfig config = ABGlobalConfig.builder().setApiUrl(url).setSensorsAnalytics(sa).enableLog(true).build();
    return new SensorsABTest(config);
  }



}
