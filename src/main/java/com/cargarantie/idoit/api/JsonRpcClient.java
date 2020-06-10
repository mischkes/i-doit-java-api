package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcRequest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;

public class JsonRpcClient {

  private static final TypeReference<Map<String, Object>> jsonMapTypeRef =
      new TypeReference<Map<String, Object>>() {
      };
  private final ObjectMapper mapper;
  private final RestClientWrapper restClient;

  public JsonRpcClient(RestClientWrapper restClient) {
    this.restClient = restClient;
    this.mapper = IdoitObjectMapper.mapper;
  }

  @SneakyThrows
  <T extends IdoitCategory> T send(CmdbCategoryRead<T> request) {
    Object resultJson = ((List<Object>) getResult(request)).get(0); //result is always a list of 1;
    return mapper.convertValue(resultJson, request.getCategoryClass());
  }

  @SneakyThrows
  <T> T send(IdoitRequest<T> request) {
    Object resultJson = getResult(request);
    return mapper.convertValue(resultJson, request.getResponseClass());
  }

  @SneakyThrows
  private <T> Object getResult(IdoitRequest<T> request) {
    request.setApiKey("c1ia5q");
    JsonRpcRequest<?> jsonRpcRequest = new JsonRpcRequest<>(request, "0");
    String requestJson = mapper.writeValueAsString(jsonRpcRequest);
    System.out.println("Request: " + requestJson);

    InputStream result = restClient.post(requestJson);

    Map<String, Object> json = mapper.readValue(result, jsonMapTypeRef);
    System.out.println("Result: " + json);

    if (json.containsKey("error")) {
      //read the error into an entity, then throw an exception
      return null;
    } else {
      System.out.println("Result: " + json.get("result"));
      //maybe throw an error if this node does not exist
      return json.get("result");
    }
  }

  //<T> Map<String, T> send(Batch<T> requests);
}
