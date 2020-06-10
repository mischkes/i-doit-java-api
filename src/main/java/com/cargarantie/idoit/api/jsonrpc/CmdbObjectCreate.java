package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;

@Data
public class CmdbObjectCreate extends IdoitRequest<ObjectCreateResponse>{
  private final String type;
  private final String title;
  private String category;
  private String purpose;
  private String cmdb_status;
  private String description;


  @Override
  public Class<ObjectCreateResponse> getResponseClass() {
    return ObjectCreateResponse.class;
  }
}
