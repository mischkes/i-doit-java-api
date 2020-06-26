package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class JsonRestClientWrapper {

  private final RestClientWrapper restClient;
  private final ObjectMapper mapper = IdoitObjectMapper.getObjectMapper();

  public JsonRestClientWrapper(RestClientWrapper restClient) {
    this.restClient = restClient;
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
