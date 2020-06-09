package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcRequest;
import com.cargarantie.idoit.api.jsonrpc.ReadResponse;
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
  <T extends IdoitCategory> ReadResponse<T> send(CmdbCategoryRead<T> request) {
    Map<String, Object> json = getJsonObject(request);

    Class<T> categoryClass = request.getCategoryClass();
    ReadResponse<T> response = new ReadResponse<>();
    Object result = ((List<Object>) json.get("result")).get(0); //there is always only one result
    T category = mapper.convertValue(result, categoryClass);
    response.setResult(category);

    return response;
  }

  @SneakyThrows
  <T> T send(IdoitRequest<T> request) {
    Map<String, Object> json = getJsonObject(request);
    T result = mapper.convertValue(json, request.getResponseClass());
    System.out.println(result);
    return result;
  }

  @SneakyThrows
  private <T> Map<String, Object> getJsonObject(IdoitRequest<T> request) {
    request.setApiKey("c1ia5q");
    JsonRpcRequest<?> jsonRpcRequest = new JsonRpcRequest<>(request, "0");
    String requestJson = mapper.writeValueAsString(jsonRpcRequest);
    System.out.println(requestJson);

    InputStream result = restClient.post(requestJson);

    Map<String, Object> json = mapper.readValue(result, jsonMapTypeRef);
    System.out.println(json);

    if (json.containsKey("error")) {
      //read the error into an entity, then throw an exception
      return null;
    }
    return json;
  }

  //<T> Map<String, T> send(Batch<T> requests);
}
