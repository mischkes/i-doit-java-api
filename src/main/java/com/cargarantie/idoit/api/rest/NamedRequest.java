package com.cargarantie.idoit.api.rest;

import lombok.Data;

@Data
public class NamedRequest<T> {
  private final String name;
  private final IdoitRequest request;
}
