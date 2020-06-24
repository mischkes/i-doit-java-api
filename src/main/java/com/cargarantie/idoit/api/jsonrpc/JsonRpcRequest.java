package com.cargarantie.idoit.api.jsonrpc;

import java.util.Map;
import lombok.Value;

@Value
public class JsonRpcRequest {

  String id;
  String method;
  Map<String, Object> params;

  public String getJsonrpc() {
    return "2.0";
  }
}
