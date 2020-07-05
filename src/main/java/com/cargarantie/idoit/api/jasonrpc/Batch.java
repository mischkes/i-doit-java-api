package com.cargarantie.idoit.api.jasonrpc;

import java.util.ArrayList;
import java.util.List;

public class Batch<T> {

  private List<NamedRequest<T>> requests = new ArrayList<>();

  public Batch<T> add(String name, IdoitRequest<T> request) {
    requests.add(new NamedRequest<>(name, request));
    return this;
  }
}
