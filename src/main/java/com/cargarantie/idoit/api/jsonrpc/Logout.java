package com.cargarantie.idoit.api.jsonrpc;

public class Logout implements IdoitRequest<SimpleSuccessResponse> {

  private static final String METHOD = "idoit.logout";

  @Override
  public Class<SimpleSuccessResponse> getResponseClass() {
    return SimpleSuccessResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
