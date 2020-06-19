package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.IdoitLogin;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResult;
import com.cargarantie.idoit.api.jsonrpc.LoginResponse;
import com.cargarantie.idoit.api.jsonrpc.NamedRequest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import lombok.SneakyThrows;

public class JsonRpcClient {

  private static final TypeReference<Map<String, Object>> jsonMapTypeRef =
      new TypeReference<Map<String, Object>>() {
      };

  private final ObjectMapper mapper;
  private final RestClientWrapper restClient;
  private final String apiKey;

  public JsonRpcClient(RestClientWrapper restClient, String apiKey) {
    this.restClient = restClient;
    this.apiKey = apiKey;
    this.mapper = IdoitObjectMapper.mapper;
  }

  public static Object expandCustomFields(Object json) {
    Map<String, Object> categoryJson = (Map<String, Object>) json;

    new HashMap<>(categoryJson).forEach((key, value) -> {
      if (value instanceof Map) {
        Map<String, Object> valueJson = (Map<String, Object>) value;
        String identifier = (String) valueJson.remove("identifier");

        if (identifier == null) {
          return;
        } else if (valueJson.isEmpty()) {
          categoryJson.remove(key);
        } else {
          categoryJson.put(identifier, valueJson);
        }
      }
    });

    return categoryJson;
  }

  void login(String username, String password) {
    MultivaluedMap<String, Object> authHeaders = new MultivaluedHashMap<>();
    authHeaders.add("X-RPC-Auth-Username", username);
    authHeaders.add("X-RPC-Auth-Password", password);
    restClient.setAuthHeaders(authHeaders);

    LoginResponse response = send(new IdoitLogin());

    MultivaluedMap<String, Object> newHeaders = new MultivaluedHashMap<>();
    newHeaders.add("X-RPC-Auth-Session", response.getSessionId());
    restClient.setAuthHeaders(authHeaders);
  }

  @SneakyThrows
  <T> Map<String, T> send(Batch<T> batch) {
    Map<String, NamedRequest<T>> requests = batch.getRequests();

    List<JsonRpcRequest<IdoitRequest<T>>> jsonRpcRequests = requests.values().stream()
        .peek(request -> request.getRequest().setApiKey(apiKey))
        .map(request -> new JsonRpcRequest<>(request.getRequest(), request.getName()))
        .collect(Collectors.toList());

    String requestJson = mapper.writeValueAsString(jsonRpcRequests);
    System.out.println("Request: " + requestJson);

    String result = restClient.post(requestJson);
    System.out.println("Result: " + result);

    JsonRpcResult[] json = mapper.readValue(result, JsonRpcResult[].class);

    Map<String, T> results = new HashMap<>();
    Stream.of(json).forEach(element -> {
      IdoitRequest<T> request = requests.get(element.getId()).getRequest();
      T t = parseResult(element, request);
      results.put(element.getId(), t);
    });

    return results;
  }

  private <T> T parseResult(JsonRpcResult jsonRpcResult, IdoitRequest<T> request) {
    //TODO: check for error
    Object result = jsonRpcResult.getResult();
    if (request instanceof CmdbCategoryRead) {
      // CmdbCategoryRead only parses non-multi list. Then, result is always a list of 1.
      List<Object> categories = (List<Object>) result;
      switch (categories.size()) {
        case 0:
          result = null;
          break;
        case 1:
          result = expandCustomFields(categories.get(0));
          break;
        default:
          throw new IllegalArgumentException("Cannot convert a multi-category response"
              + " to a single category result");
      }
    }
    T t = mapper.convertValue(result, request.getResponseClass());
    return t;
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
    request.setApiKey(apiKey);
    JsonRpcRequest<?> jsonRpcRequest = new JsonRpcRequest<>(request, "0");
    String requestJson = mapper.writeValueAsString(jsonRpcRequest);
    System.out.println("Request: " + requestJson);

    String result = restClient.post(requestJson);
    System.out.println("Result: " + result);

    Map<String, Object> json = mapper.readValue(result, jsonMapTypeRef);

    if (json.containsKey("error")) {
      //read the error into an entity, then throw an exception
      return null;
    } else {
      //maybe throw an error if this node does not exist
      return json.get("result");
    }
  }

  //<T> Map<String, T> send(Batch<T> requests);
}
