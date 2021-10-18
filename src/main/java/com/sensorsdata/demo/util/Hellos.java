package com.sensorsdata.demo.util;

/**
 * TODO
 *
 * @author fangzhuo
 * @version 1.0.0
 * @since 2021/09/30 11:02
 */
public class Hellos {

  private static final String PREFIX = "/edge_general_proxy";

  public static void main(String[] args) {
    String path = "/edge_general_proxy/${module}/";
    final String[] split = path.substring(PREFIX.length()).split("/");
    for (String s : split) {
      System.out.println(s);
    }
  }
}
