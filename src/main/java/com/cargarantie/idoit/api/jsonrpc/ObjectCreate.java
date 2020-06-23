package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.util.Util;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ObjectCreate extends IdoitRequest<ObjectCreateResponse> {

  public static final String METHOD = "cmdb.object.create";
  private final String type;
  private final String title;
  private String category;
  private String purpose;
  private String cmdb_status;
  private String description;

  public ObjectCreate(IdoitObject object) {
    this.type = Util.getObjectTypeName(object);
    this.title = object.getTitle();
  }

  @Override
  public Class<ObjectCreateResponse> getResponseClass() {
    return ObjectCreateResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
