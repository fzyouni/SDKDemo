package com.sensorsdata.demo.test;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.bean.EventRecord;
import com.sensorsdata.analytics.javasdk.bean.FailedData;
import com.sensorsdata.analytics.javasdk.consumer.Callback;
import com.sensorsdata.analytics.javasdk.consumer.FastBatchConsumer;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * FastBatchConsumer 示例代码
 *
 * @author fangzhuo
 * @version 1.0.0
 * @since 2021/12/27 14:14
 */
public class HelloFastBatchConsumer {

  public static void main(String[] args) throws InvalidArgumentException {
    //发送失败的数据，收集容器。（仅测试使用，避免网络异常时，大批量数据发送失败同时保存内存中，导致 OOM ）
    final List<FailedData> failedDataList = new ArrayList<>();

    //参数含义：数据接收地址，是否定时上报，非定时上报阈值触发条数，内部缓存最大容量，定时检查时间频率，网络请求超时时间，请求失败之后异常数据回调
    final FastBatchConsumer fastBatchConsumer =
        new FastBatchConsumer("serviceUri", false, 50, 6000, 2, 3, new Callback() {
          @Override
          public void onFailed(FailedData failedData) {
            //收集发送失败的数据（在线上环境，建议持久化保存）
            failedDataList.add(failedData);
          }
        });
    //构建 sa 实例对象
    final SensorsAnalytics sa = new SensorsAnalytics(fastBatchConsumer);

    //do something track event
    sa.track(EventRecord.builder().build());

    //异常数据的重发送设置(重发送尽量由另外一个单独的线程来处理完成，避免影响主线程处理逻辑)
    if (!failedDataList.isEmpty()) {
      for (FailedData failedData : failedDataList) {
        try {
          //返回重发送接口发送成功与否，true:发送成功；false:发送失败
          boolean b = fastBatchConsumer.resendFailedData(failedData);
        } catch (Exception e) {
          //处理重发送数据校验异常的情况
          e.printStackTrace();
        }
      }
    }

  }
}
