package com.cargarantie.idoit.api.model;

import java.util.Arrays;

public enum StandardCategories implements IdoitModelReference {
  General("C__CATG__GLOBAL", CategoryGeneral.class),
  ContactAssignment("C__CATG__CONTACT", CategoryContactAssignment.class);

  private final String modelName;
  private final Class<?> modelClass;

  StandardCategories(String modelName, Class<?> modelClass) {
    this.modelName = modelName;
    this.modelClass = modelClass;
  }

  @Override
  public String modelName() {
    return modelName;
  }

  @Override
  public Class<?> modelClass() {
    return modelClass;
  }
}
