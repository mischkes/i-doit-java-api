package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cargarantie.idoit.api.model.CategoryName;
import com.cargarantie.idoit.api.model.IdoitCategory;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class CategoryReadTest {

  @Order(0)
  @Test
  void getCategory_shouldThrowException_ifCategoryClassNotRegisterd() {
    CategoryRead<MyCat> read = new CategoryRead<>(null, MyCat.class);

    assertThatThrownBy(() -> read.getCategory()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No model registered for class"
            + " com.cargarantie.idoit.api.jsonrpc.CategoryReadTest$MyCat");
  }

  @Order(1)
  @Test
  void getCategory_shouldReturnRegisteredName() {
    CategoryRead<MyCat> read = new CategoryRead<>(null, MyCat.class);

    assertThat(read.getCategory()).isEqualTo("CAT_MYCAT");
  }

  @CategoryName("CAT_MYCAT")
  public static class MyCat extends IdoitCategory {

  }
}
