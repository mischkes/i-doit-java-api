package com.cargarantie.idoit.api.jasonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CmdbCategoryCreateTest {
  @Test
  void testMethodString() {
    CmdbCategoryCreate obj = new CmdbCategoryCreate(0, null);
    assertThat(obj.getMethod()).isEqualTo("cmdb.category.create");
  }
}
