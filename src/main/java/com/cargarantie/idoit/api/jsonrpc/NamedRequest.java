package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;

@Data
public class NamedRequest<T> {
  private final String name;
  private final IdoitRequest<T> request;

  public NamedRequest(String name, IdoitRequest<T> request) {
    this.name = name;
    this.request = request;
  }
}
