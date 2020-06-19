package com.cargarantie.idoit.api;

import lombok.SneakyThrows;

public class IdoitClient {

  private final String url;
  private final String apiKey;
  private final String username;
  private final String password;

  public IdoitClient(String url, String apiKey, String username, String password) {
    this.url = url;
    this.apiKey = apiKey;
    this.username = username;
    this.password = password;
  }

  public IdoitClient(ClientConfig cfg) {
    this(cfg.getUrl(), cfg.getApiKey(), cfg.getUsername(), cfg.getPassword());
  }

  @SneakyThrows
  public IdoitSession login() {
    return new IdoitSession(url, apiKey, username, password);
  }
}
