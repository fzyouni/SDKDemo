package com.sensorsdata.demo.config;



import com.sensorsdata.analytics.javasdk.ISensorsABTest;
import com.sensorsdata.analytics.javasdk.ISensorsAnalytics;
import com.sensorsdata.analytics.javasdk.SensorsABTest;
import com.sensorsdata.analytics.javasdk.bean.ABGlobalConfig;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

/**
 * 构建静态工厂
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/06/29 11:40
 */
public class ABTestInstanceFactory {

  public static ISensorsABTest getABTestConfigInstance(String url, ISensorsAnalytics sa)
      throws InvalidArgumentException {
    ABGlobalConfig config = ABGlobalConfig.builder().setApiUrl(url).setSensorsAnalytics(sa).build();
    return new SensorsABTest(config);
  }

}
