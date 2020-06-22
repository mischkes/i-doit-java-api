package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CategorySaveTest {
  @Test
  void testMethodString() {
    CategorySave obj = new CategorySave(null, null);
    assertThat(obj.getMethod()).isEqualTo("cmdb.category.create");
  }
}
