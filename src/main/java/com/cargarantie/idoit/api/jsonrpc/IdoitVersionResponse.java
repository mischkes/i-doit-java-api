package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;

@Data
public class IdoitVersionResponse {

  private Login login;
  private String version;
  private String step;
  private String type;

  @Data
  public static class Login {

    private int userid;
    private String name;
    private String mail;
    private String username;
    private String mandator;
    private String language;
  }
}
