package com.cargarantie.idoit.api.jsonrpc;

import lombok.Value;

@Value
public class JsonRpcResponse {

  String id;
  Object result;
  Object error;

  public boolean hasError() {
    return error != null;
  }
}
