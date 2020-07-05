package com.cargarantie.idoit.api.jasonrpc;

import lombok.Data;

@Data
public class NamedRequest<T> {
  private final String name;
  private final IdoitRequest request;
}
