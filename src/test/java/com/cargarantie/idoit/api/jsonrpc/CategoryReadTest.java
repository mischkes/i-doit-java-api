package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.annotation.CategoryName;
import org.junit.jupiter.api.Test;

class CategoryReadTest {

  @Test
  void getCategory_shouldThrowException_ifCategoryClassHasNoName() {
    CategoryRead<MyCatNoName> read = new CategoryRead<>(null, MyCatNoName.class);

    assertThatThrownBy(() -> read.getCategory()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Class com.cargarantie.idoit.api.jsonrpc.CategoryReadTest.MyCatNoName"
            + " has missing CategoryName annotation value");
  }

  @Test
  void getCategory_shouldReturnRegisteredName() {
    CategoryRead<MyCat> read = new CategoryRead<>(null, MyCat.class);

    assertThat(read.getCategory()).isEqualTo("CAT_MYCAT");
  }

  public static class MyCatNoName extends IdoitCategory {

  }

  @CategoryName("CAT_MYCAT")
  public static class MyCat extends IdoitCategory {

  }
}
