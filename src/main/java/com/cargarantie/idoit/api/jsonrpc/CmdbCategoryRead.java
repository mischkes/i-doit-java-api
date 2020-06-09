package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CmdbCategoryRead<T extends IdoitCategory> extends IdoitRequest<ReadResponse<T>> {
  @JsonProperty("objID")
  int objId;
  Class<T> category;

  public void setCategory(String category) {
    this.category = (Class<T>) AllModels.getClass(category);
  }

  public String getCategory() {
    return AllModels.getName(category);
  }

  @JsonIgnore
  public Class<T> getCategoryClass() {
    return category;
  }

  @Override
  public Class<ReadResponse<T>> getResponseClass() {
    return null; //this request has special handling
  }
}
