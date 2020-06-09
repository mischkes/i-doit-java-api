package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AllModelsTest {

  @Test
  void register_shouldEnableGetName() {
    AllModels.register("integerModel", Integer.class);

    assertThat(AllModels.getName(new Integer(42))).isEqualTo("integerModel");
  }

  @Test
  void staticConstruction_shouldRegisterCategoryGeneral() {
    assertThat(AllModels.getName(new CategoryGeneral())).isEqualTo("C__CATG__GLOBAL");
  }
}
