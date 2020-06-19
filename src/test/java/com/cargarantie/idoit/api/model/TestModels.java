package com.cargarantie.idoit.api.model;

public class TestModels {
  public static void register() {
    AllModels.register("C__CATG__CUSTOM_FIELDS_CLIENT_DESCRIPTION", IdoitCategoryClientDescriptionSimple.class);
    AllModels.register("C__OBJTYPE__CLIENT", IdoitObjectClientSimple.class);
  }
}
