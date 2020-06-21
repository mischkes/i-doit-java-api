package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CmdbCategorySaveTest {
  @Test
  void testMethodString() {
    CmdbCategorySave obj = new CmdbCategorySave(null, null);
    assertThat(obj.getMethod()).isEqualTo("cmdb.category.create");
  }
}
