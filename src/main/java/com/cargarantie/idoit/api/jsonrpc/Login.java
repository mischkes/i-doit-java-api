package com.cargarantie.idoit.api.jsonrpc;

public class Login extends IdoitRequest<LoginResponse> {

  public static final String METHOD = "cmdb.idoit.login";

  @Override
  public Class<LoginResponse> getResponseClass() {
    return LoginResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
