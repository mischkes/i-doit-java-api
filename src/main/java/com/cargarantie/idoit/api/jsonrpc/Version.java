package com.cargarantie.idoit.api.jsonrpc;


public class Version implements IdoitRequest<VersionResponse> {

  private static final String METHOD = "idoit.version";

  @Override
  public Class<VersionResponse> getResponseClass() {
    return VersionResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
