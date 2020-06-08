package com.cargarantie.idoit.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class IdoitCategory {

  private String description;
  private int id;

  public String categoryName() {
    if (this.getClass().isAnnotationPresent(IdoitCategoryName.class)) {
      return getClass().getAnnotation(IdoitCategoryName.class).value();
    } else {
      throw new IllegalStateException("IdoitCategory classes must specify the IdoitCategoryName"
          + " annotation");
    }
  }
}
