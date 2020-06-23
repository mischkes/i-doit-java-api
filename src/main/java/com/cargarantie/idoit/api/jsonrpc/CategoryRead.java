package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRead<T extends IdoitCategory> extends IdoitRequest<T> {

  public static final String METHOD = "cmdb.category.read";

  @JsonProperty("objID")
  private ObjectId objId;
  private Class<T> category;

  public String getCategory() {
    return Util.getCategoryName(category);
  }

  @JsonIgnore
  public Class<T> getCategoryClass() {
    return category;
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
