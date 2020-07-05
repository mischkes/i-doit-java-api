package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class IdoitCategory {

  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PRIVATE)
  private CategoryId id;

  @JsonProperty(value = "objID", access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PRIVATE)
  private ObjectId objId;

  private String description;
}
