package com.sensorsdata.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动 Spring 容器主程序，如果需要容器环境，那就启动该程序，然后，可以通过 postman 访问 http://localhost:8088/XXX
 * XXX 对应 TestController 类中的 @GetMapping 内的值
 * 表示 接口访问后端方法
 *
 * 如果不想启动容器，直接去运行 test 文件夹目录下对应的 Java 类即可
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ImportResource(locations = {"classpath:spring-config.xml"})
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
