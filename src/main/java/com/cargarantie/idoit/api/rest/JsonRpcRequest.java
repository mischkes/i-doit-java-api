package com.cargarantie.idoit.api.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonRpcRequest<T extends IdoitRequest> {
  private T params;
  private String id;

  public String version() {
    return "2.0";
  }
  public String method() {
    return params.getMethod();
  }
}
