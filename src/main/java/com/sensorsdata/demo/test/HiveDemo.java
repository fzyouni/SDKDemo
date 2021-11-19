package com.sensorsdata.demo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author fangzhuo
 * @version 1.0.0
 * @since 2021/11/19 10:13
 */
public class HiveDemo {

  public static void main(String[] args) {
    String driverName = "org.apache.hive.jdbc.HiveDriver";
    String url =
        "jdbc:hive2://hybrid01.classic-tx-beijing-01.sdg-rd-dev-importer-0813.deploy.sensorsdata.cloud:21050/rawdata;auth=noSasl";
    Connection connection = null;
    try {
      Class.forName(driverName);
      connection = DriverManager.getConnection(url, "", "");
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    if (connection != null) {
      try {
        ResultSet rs =
            connection.prepareStatement("select event_id,distinct_id from event_wos_p2 limit 5").executeQuery();
        while (rs.next()) {
          System.out.println(rs.getInt("event_id"));
          System.out.println(rs.getString("distinct_id"));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


  }


}
