package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IdoitRequest<T> {

  @JsonIgnore
  Class<T> getResponseClass();

  @JsonIgnore
  String getMethod();
}
