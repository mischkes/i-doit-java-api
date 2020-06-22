package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
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
    return Optional.ofNullable(AllModels.getName(category))
        .orElseThrow(() -> new IllegalStateException(
            "Class " + category + " is not registered as a category in " + AllModels.class));
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
