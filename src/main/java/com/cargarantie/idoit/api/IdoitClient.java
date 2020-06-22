package com.cargarantie.idoit.api;

import lombok.SneakyThrows;

public class IdoitClient {

  private final ClientConfig cfg;

  public IdoitClient(ClientConfig cfg) {
    this.cfg = cfg;
  }

  @SneakyThrows
  public IdoitSession login() {
    return new IdoitSession(cfg);
  }
}
