package com.cargarantie.idoit.api.config;

import lombok.Value;

@Value
public class ClientConfig {

  String url;
  String apiKey;
  String username;
  String password;

  public String getApiEndpoint() {
    return url + "/src/jsonrpc.php";
  }
}
