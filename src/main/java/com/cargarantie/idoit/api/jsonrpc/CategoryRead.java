package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CategoryRead<T extends IdoitCategory> implements IdoitRequest<T> {

  private static final String METHOD = "cmdb.category.read";

  @JsonProperty("objID")
  ObjectId objId;
  Class<T> category;

  public String getCategory() {
    return Util.getCategoryName(category);
  }

  @Override
  public Class<T> getResponseClass() {
    return category;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
