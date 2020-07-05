package com.cargarantie.idoit.api.jsonrpc;

public class Login implements IdoitRequest<LoginResponse> {

  private static final String METHOD = "idoit.login";

  @Override
  public Class<LoginResponse> getResponseClass() {
    return LoginResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
