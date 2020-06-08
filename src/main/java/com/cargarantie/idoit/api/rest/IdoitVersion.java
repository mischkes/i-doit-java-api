package com.cargarantie.idoit.api.rest;


public class IdoitVersion extends IdoitRequest<IdoitVersionResponse>{

  @Override
  public Class<IdoitVersionResponse> getResponseClass() {
    return IdoitVersionResponse.class;
  }
}
