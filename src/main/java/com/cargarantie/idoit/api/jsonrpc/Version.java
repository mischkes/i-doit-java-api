package com.cargarantie.idoit.api.jsonrpc;


public class Version extends IdoitRequest<VersionResponse> {

  public static final String METHOD = "idoit.version";

  @Override
  public Class<VersionResponse> getResponseClass() {
    return VersionResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
