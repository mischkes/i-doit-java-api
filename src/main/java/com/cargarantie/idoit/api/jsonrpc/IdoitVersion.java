package com.cargarantie.idoit.api.jsonrpc;


public class IdoitVersion extends IdoitRequest<IdoitVersionResponse>{

  @Override
  public Class<IdoitVersionResponse> getResponseClass() {
    return IdoitVersionResponse.class;
  }
}
