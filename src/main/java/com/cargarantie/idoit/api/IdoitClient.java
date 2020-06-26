package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.ClientConfig;

public class IdoitClient {

  private final ClientConfig cfg;

  public IdoitClient(ClientConfig cfg) {
    this.cfg = cfg;
  }

  public IdoitSession login() {
    return new IdoitSession(cfg);
  }
}
