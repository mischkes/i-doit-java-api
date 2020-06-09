package com.cargarantie.idoit.api.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonRpcRequest<T extends IdoitRequest> {
  private T params;
  private String id;

  public String getJsonrpc() {
    return "2.0";
  }
  public String getMethod() {
    return params.getMethod();
  }
}
