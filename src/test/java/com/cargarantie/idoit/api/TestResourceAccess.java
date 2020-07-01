package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Scanner;
import lombok.SneakyThrows;

public class TestResourceAccess {

  protected final ObjectMapper mapper;

  public TestResourceAccess() {
    mapper = IdoitObjectMapper.getObjectMapper();
    mapper.enable(Feature.ALLOW_SINGLE_QUOTES);
  }

  @SneakyThrows
  public Object parseJson(String json) {
    return mapper.readValue(json, Object.class);
  }

  public String getResourceAsString(String path) {
    InputStream stream = getClass().getResourceAsStream(path);

    // use the "Stupid Scanner" trick from
    // https://community.oracle.com/blogs/pat/2004/10/23/stupid-scanner-tricks
    return new Scanner(stream, "UTF-8").useDelimiter("\\A").next();
  }

  @SneakyThrows
  public <T> T getJson(String fileName, Class<T> resultClass) {
    return mapper.readValue(getJsonString(fileName), resultClass);
  }

  @SneakyThrows
  public String getJsonString(String fileName) {
    return getResourceAsString(getClass().getSimpleName() + "/" + fileName + ".json");
  }
}
