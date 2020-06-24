package com.cargarantie.idoit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonRestClientWrapper {

  private final RestClientWrapper restClient;
  private final ObjectMapper mapper;

  public JsonRestClientWrapper(RestClientWrapper restClient, ObjectMapper mapper) {
    this.restClient = restClient;
    this.mapper = mapper;
  }

  @SneakyThrows
  public <T> T send(Object jsonRpc, Class<T> rpcResponseClass) {
    String requestJson = mapper.writeValueAsString(jsonRpc);
    log.debug("Request: {}", requestJson);

    String response = restClient.post(requestJson);
    log.debug("Result: {}", response);

    return mapper.readValue(response, rpcResponseClass);
  }
}
