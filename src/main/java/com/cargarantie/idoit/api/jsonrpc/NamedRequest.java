package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;

@Data
public class NamedRequest<T> {
  private final String name;
  private final IdoitRequest request;
}
