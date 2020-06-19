package com.cargarantie.idoit.api;

import lombok.Data;

@Data
public class ClientConfig {

  private final String url;
  private final String apiKey;
  private final String username;
  private final String password;
}
