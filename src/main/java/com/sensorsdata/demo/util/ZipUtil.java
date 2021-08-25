package com.sensorsdata.demo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * TODO
 *
 * @author fz
 * @version 1.0.0
 * @since 2021/07/20 15:21
 */
public class ZipUtil {


  private static final String ISO = "ISO-8859-1";

  // 压缩
  public static String compress(String str) throws IOException {
    if (str == null || str.length() == 0) {
      return str;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = new GZIPOutputStream(out);
    gzip.write(str.getBytes());
    gzip.close();
    return out.toString(ZipUtil.ISO);
  }


  // 解压缩
  public static String uncompress(String str) throws IOException {
    if (str == null || str.length() == 0) {
      return str;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(ZipUtil.ISO));
    GZIPInputStream gunzip = new GZIPInputStream(in);
    byte[] buffer = new byte[256];
    int n;
    while ((n = gunzip.read(buffer)) >= 0) {
      out.write(buffer, 0, n);
    }
    gunzip.close();
    return out.toString();
  }

  public static boolean isGZipped(String str) throws UnsupportedEncodingException {
    byte[] bytes = str.getBytes(ZipUtil.ISO);
    if (bytes[0] == 0x1F && bytes[1] == 0x8B) {
      return true;
    }
    return false;
  }


  public static void main(String[] args) {
    String json =
        "{\"synced_source_progress\":{\"f\":\"(dev=fc00,ino=1572916)\",\"o\":1045888,\"n\":\"access.log.2021-07-04-16\",\"s\":25080124,\"c\":25080124,\"e\":\"LogAgent.679\"},\"sended_source_progress\":{\"f\":\"(dev=fc00,ino=1572916)\",\"o\":1045888,\"n\":\"access.log.2021-07-04-16\",\"s\":25080124,\"c\":25080124,\"e\":\"LogAgent.679\"},\"kafka_progress\":{\"0\":{\"offset\":186857253230,\"partition_id\":0,\"update_timestamp\":1625385766135},\"1\":{\"offset\":186987181832,\"partition_id\":1,\"update_timestamp\":1625385766135},\"2\":{\"offset\":186628147112,\"partition_id\":2,\"update_timestamp\":1625385766135},\"3\":{\"offset\":186754433015,\"partition_id\":3,\"update_timestamp\":1625385766135},\"4\":{\"offset\":186862034423,\"partition_id\":4,\"update_timestamp\":1625385766135},\"5\":{\"offset\":187138184070,\"partition_id\":5,\"update_timestamp\":1625385766135},\"6\":{\"offset\":187165895385,\"partition_id\":6,\"update_timestamp\":1625385766135},\"7\":{\"offset\":188233154633,\"partition_id\":7,\"update_timestamp\":1625385766135},\"8\":{\"offset\":187158615648,\"partition_id\":8,\"update_timestamp\":1625385766135},\"9\":{\"offset\":186894797109,\"partition_id\":9,\"update_timestamp\":1625385766135},\"10\":{\"offset\":92042438444,\"partition_id\":10,\"update_timestamp\":1625385766135},\"11\":{\"offset\":92399828285,\"partition_id\":11,\"update_timestamp\":1625385766135},\"12\":{\"offset\":92321144845,\"partition_id\":12,\"update_timestamp\":1625385766135},\"13\":{\"offset\":92562958814,\"partition_id\":13,\"update_timestamp\":1625385766135},\"14\":{\"offset\":92138887381,\"partition_id\":14,\"update_timestamp\":1625385766135},\"15\":{\"offset\":92432959060,\"partition_id\":15,\"update_timestamp\":1625385766135},\"16\":{\"offset\":91936098235,\"partition_id\":16,\"update_timestamp\":1625385766135},\"17\":{\"offset\":92327312249,\"partition_id\":17,\"update_timestamp\":1625385766135},\"18\":{\"offset\":92172575914,\"partition_id\":18,\"update_timestamp\":1625385766135},\"19\":{\"offset\":92513678034,\"partition_id\":19,\"update_timestamp\":1625385766135},\"23\":{\"offset\":14212105718,\"partition_id\":23,\"update_timestamp\":1625385766135},\"21\":{\"offset\":14192456931,\"partition_id\":21,\"update_timestamp\":1625385766135},\"24\":{\"offset\":14197934335,\"partition_id\":24,\"update_timestamp\":1625385766135},\"25\":{\"offset\":14238236223,\"partition_id\":25,\"update_timestamp\":1625385766135},\"22\":{\"offset\":14180501695,\"partition_id\":22,\"update_timestamp\":1625385766135},\"20\":{\"offset\":14238249206,\"partition_id\":20,\"update_timestamp\":1625385766135},\"26\":{\"offset\":14169833074,\"partition_id\":26,\"update_timestamp\":1625385766135},\"31\":{\"offset\":11134436303,\"partition_id\":31,\"update_timestamp\":1625385766135},\"29\":{\"offset\":11166510211,\"partition_id\":29,\"update_timestamp\":1625385766135},\"35\":{\"offset\":11178171993,\"partition_id\":35,\"update_timestamp\":1625385766135},\"38\":{\"offset\":11162763732,\"partition_id\":38,\"update_timestamp\":1625385766135},\"33\":{\"offset\":11069553102,\"partition_id\":33,\"update_timestamp\":1625385766135},\"32\":{\"offset\":11120660226,\"partition_id\":32,\"update_timestamp\":1625385766135},\"34\":{\"offset\":11137620931,\"partition_id\":34,\"update_timestamp\":1625385766135},\"36\":{\"offset\":11118943976,\"partition_id\":36,\"update_timestamp\":1625385766135},\"30\":{\"offset\":11169389104,\"partition_id\":30,\"update_timestamp\":1625385766135},\"39\":{\"offset\":11112936619,\"partition_id\":39,\"update_timestamp\":1625385766135},\"37\":{\"offset\":11142003639,\"partition_id\":37,\"update_timestamp\":1625385766135},\"27\":{\"offset\":11148635531,\"partition_id\":27,\"update_timestamp\":1625385766135},\"28\":{\"offset\":11116436833,\"partition_id\":28,\"update_timestamp\":1625385766135}},\"last_update_time\":1625385677541,\"last_sync_time\":1625385766135,\"status\":{\"start_times\":0,\"this_time_start_running_time\":0,\"sending_speed\":0.0,\"sending_records_in_store\":0,\"counter_filtered_by_expired_time\":0,\"counter_invalid_log_entry\":0,\"counter_invalid_reader_log_entry\":0,\"sent_to_kafka\":0,\"raw_read_count\":0,\"extra_data_count\":0},\"do_not_need_find_in_kafka\":null}";
    System.out.println(json);
    System.out.println(String.format("压缩前字符串长度：%d", json.length()));
    try {
      String zipJson = ZipUtil.compress(json);
      System.out.println(zipJson);
      System.out.println(String.format("压缩后字符串长度：%d", zipJson.length()));
      System.out.println("是否为GZIP字符串：" + ZipUtil.isGZipped(zipJson));
      System.out.println("解压之后的字符串：" + ZipUtil.uncompress(zipJson));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
