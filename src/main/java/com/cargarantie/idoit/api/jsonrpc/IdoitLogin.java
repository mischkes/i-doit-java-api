package com.cargarantie.idoit.api.jsonrpc;

public class IdoitLogin extends IdoitRequest<LoginResponse> {

  @Override
  public Class<LoginResponse> getResponseClass() {
    return LoginResponse.class;
  }
}
