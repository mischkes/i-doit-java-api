package com.cargarantie.idoit.api.jsonrpc;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VersionResponse {

  Login login;
  String version;
  String step;
  String type;

  @Value
  @Builder
  public static class Login {

    int userid;
    String name;
    String mail;
    String username;
    String tenant;
    String language;
  }
}
