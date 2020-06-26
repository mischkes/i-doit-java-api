package com.cargarantie.idoit.api.jsonrpc;

import lombok.Value;

@Value
public class NamedRequest<T> {

  String name;
  IdoitRequest<T> request;
}
