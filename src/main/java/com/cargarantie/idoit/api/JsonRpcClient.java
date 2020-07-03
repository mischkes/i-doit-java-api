package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcRequest;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResponse;
import com.cargarantie.idoit.api.jsonrpc.Login;
import com.cargarantie.idoit.api.jsonrpc.LoginResponse;
import com.cargarantie.idoit.api.jsonrpc.Logout;
import com.cargarantie.idoit.api.jsonrpc.NamedRequest;
import com.cargarantie.idoit.api.model.exception.IdoitException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import lombok.SneakyThrows;

public class JsonRpcClient {

  private final RestClientWrapper restClient;
  private final String apiKey;
  private final JsonRestClientWrapper jsonRestClient;
  private final ObjectMapper mapper = IdoitObjectMapper.getObjectMapper();

  JsonRpcClient(RestClientWrapper restClient, String apiKey) {
    this(restClient, apiKey, new JsonRestClientWrapper(restClient));
  }

  JsonRpcClient(RestClientWrapper restClient, String apiKey, JsonRestClientWrapper jsonRestClient) {
    this.restClient = restClient;
    this.apiKey = apiKey;
    this.jsonRestClient = jsonRestClient;
  }

  public <T> T send(IdoitRequest<T> request) {
    JsonRpcRequest jsonRpcRequest = newJsonRpcRequest(request, "0");
    JsonRpcResponse result = jsonRestClient.send(jsonRpcRequest, JsonRpcResponse.class);
    return parseResult(result, request);
  }

  public <T> Map<String, T> send(Batch<T> batch) {
    Map<String, NamedRequest<T>> requests = batch.getRequests();
    List<JsonRpcRequest> jsonRpcRequests = requests.values().stream()
        .map(request -> newJsonRpcRequest(request.getRequest(), request.getName()))
        .collect(Collectors.toList());

    if (jsonRpcRequests.isEmpty()) {
      return new HashMap<>();
    }

    JsonRpcResponse[] jsonRpcResponses = jsonRestClient
        .send(jsonRpcRequests, JsonRpcResponse[].class);

    LinkedHashMap<String, T> results = new LinkedHashMap<>();
    Stream.of(jsonRpcResponses).forEach(element -> {
      IdoitRequest<T> request = requests.get(element.getId()).getRequest();
      T result = parseResult(element, request);
      results.put(element.getId(), result);
    });

    return results;
  }

  void login(String username, String password) {
    MultivaluedMap<String, Object> authHeaders = new MultivaluedHashMap<>();
    authHeaders.add("X-RPC-Auth-Username", username);
    authHeaders.add("X-RPC-Auth-Password", password);
    restClient.setAuthHeaders(authHeaders);

    LoginResponse response = send(new Login());

    MultivaluedMap<String, Object> newHeaders = new MultivaluedHashMap<>();
    newHeaders.add("X-RPC-Auth-Session", response.getSessionId());
    restClient.setAuthHeaders(newHeaders);
  }

  void logout() {
    send(new Logout());
    restClient.setAuthHeaders(new MultivaluedHashMap<>());
  }

  private JsonRpcRequest newJsonRpcRequest(IdoitRequest<?> request, String id) {
    Map<String, Object> map = (Map<String, Object>) mapper.convertValue(request, Object.class);
    map.put("apikey", apiKey);
    return new JsonRpcRequest(id, request.getMethod(), map);
  }

  @SneakyThrows
  private <T> T parseResult(JsonRpcResponse jsonRpcResponse, IdoitRequest<T> request) {

    if (jsonRpcResponse.hasError()) {
      throw new IdoitException(String.format("Received error <%s> for request <%s>",
          jsonRpcResponse.getError(), request));
    } else if (jsonRpcResponse.getResult() == null) {
      throw new IdoitException("Result is null for request <" + request + ">");
    } else {
      return request.mapResponse(jsonRpcResponse.getResult());
    }
  }
}
