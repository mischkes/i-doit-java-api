package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CmdbObjectCreate extends IdoitRequest<ObjectCreateResponse>{
  private final String type;
  private final String title;
  private String category;
  private String purpose;
  private String cmdb_status;
  private String description;

  public CmdbObjectCreate(IdoitObject object) {
    this.type = AllModels.getName(object);
    this.title = object.getTitle();
  }

  @Override
  public Class<ObjectCreateResponse> getResponseClass() {
    return ObjectCreateResponse.class;
  }
}
