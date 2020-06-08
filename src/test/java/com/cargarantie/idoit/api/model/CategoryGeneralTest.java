package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryGeneralTest {
  @Test
  void testCategoryName() {
    assertThat(new CategoryGeneral().categoryName()).isEqualTo("C__CATG__GLOBAL");
  }

}
