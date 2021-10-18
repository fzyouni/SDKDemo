package com.sensorsdata.demo.service;

import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Map;

/**
 * TODO
 *
 * @author fz <fangzhuo@sensorsdata.cn>
 * @version 1.0.0
 * @since 2021/06/22 11:17
 */
public interface ITestService {

  String hello(String userId) throws InvalidArgumentException;

  String abTest(String userId, String experimentVariableName, String defaultValue)
      throws InvalidArgumentException, IOException;

  String generateLog(Integer integer) throws InvalidArgumentException;

  void asycLog() throws InvalidArgumentException;

  Long syncUserLog() throws InvalidArgumentException;

  String visitHome(String cookieId) throws InvalidArgumentException, JsonProcessingException;

  String searchGoods(String cookieId) throws InvalidArgumentException, JsonProcessingException;

  String signup(String cookieId, String userId) throws InvalidArgumentException;

  String userInfo(String userId, String name, Integer age, String gender, String registerDate)
      throws InvalidArgumentException, JsonProcessingException;

  String itemInfo(Map<String, Object> params)
      throws InvalidArgumentException, JsonProcessingException;

  void flush();

  String fastFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException;

  String asyncFetchABTest(String userId, String experimentVariableName, String defaultValue)
      throws JsonProcessingException;
}
