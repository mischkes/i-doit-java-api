package com.cargarantie.idoit.api.jasonrpc;


public class IdoitVersion extends IdoitRequest<IdoitVersionResponse>{

  @Override
  public Class<IdoitVersionResponse> getResponseClass() {
    return IdoitVersionResponse.class;
  }
}
