package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AllIdoitModelsTest {

  @Test
  void register_shouldEnableGetName() {
    AllIdoitModels.register("integerModel", Integer.class);

    assertThat(AllIdoitModels.getName(new Integer(42))).isEqualTo("integerModel");
  }

  @Test
  void staticConstruction_shouldRegisterCategoryGeneral() {
    assertThat(AllIdoitModels.getName(new CategoryGeneral())).isEqualTo("C__CATG__GLOBAL");
  }
}
