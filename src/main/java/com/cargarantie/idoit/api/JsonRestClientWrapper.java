package com.cargarantie.idoit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import lombok.SneakyThrows;

public class JsonRestClientWrapper {

  private final RestClientWrapper restClient;
  private final ObjectMapper mapper;

  public JsonRestClientWrapper(RestClientWrapper restClient,
      ObjectMapper mapper) {
    this.restClient = restClient;
    this.mapper = mapper;
  }

  @SneakyThrows
  public <T> T send(Object jsonRpc, Class<T> rpcResponseClass) {
    String requestJson = mapper.writeValueAsString(jsonRpc);
    System.out.println("Request: " + requestJson);

    String response = restClient.post(requestJson);
    System.out.println("Result: " + response);

    return mapper.readValue(response, rpcResponseClass);
  }
}
