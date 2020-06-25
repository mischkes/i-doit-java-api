package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ObjectCreate implements IdoitRequest<ObjectCreateResponse> {

  private static final String METHOD = "cmdb.object.create";
  String type;
  String title;
  String category;
  String purpose;
  String cmdbStatus;
  String description;

  public ObjectCreate(IdoitObject object) {
    this(Util.getObjectTypeName(object), object.getTitle());
  }

  public ObjectCreate(String type, String title) {
    this.type = type;
    this.title = title;
    this.category = null;
    this.purpose = null;
    this.cmdbStatus = null;
    this.description = null;
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
