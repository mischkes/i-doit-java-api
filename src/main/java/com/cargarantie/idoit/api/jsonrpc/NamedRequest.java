package com.cargarantie.idoit.api.jsonrpc;

import lombok.Value;

@Value
public class NamedRequest<T> {

  String name;
  IdoitRequest<T> request;

  public NamedRequest(String name, IdoitRequest<T> request) {
    this.name = name;
    this.request = request;
  }
}
