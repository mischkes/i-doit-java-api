package com.cargarantie.idoit.api.jsonrpc;

public class IdoitLogout extends IdoitRequest<SimpleSuccessResponse> {

  @Override
  public Class<SimpleSuccessResponse> getResponseClass() {
    return SimpleSuccessResponse.class;
  }
}
