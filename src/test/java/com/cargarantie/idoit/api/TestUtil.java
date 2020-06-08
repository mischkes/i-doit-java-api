package com.cargarantie.idoit.api;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class TestUtil {

  @SneakyThrows
  public static Object parseJson(String json) {
    ObjectMapper mapper = getObjectMapper();
    return mapper.readValue(json, Object.class);
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(Feature.ALLOW_SINGLE_QUOTES);
    return mapper;
  }

}
