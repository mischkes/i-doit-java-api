package com.cargarantie.idoit.api;

public class IdoitClient {

  private final ClientConfig cfg;

  public IdoitClient(ClientConfig cfg) {
    this.cfg = cfg;
  }

  public IdoitSession login() {
    return new IdoitSession(cfg);
  }
}
