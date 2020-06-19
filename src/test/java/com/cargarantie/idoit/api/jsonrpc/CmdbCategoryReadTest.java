package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class CmdbCategoryReadTest {

  @Order(0)
  @Test
  void getCategory_shouldThrowException_ifCategoryClassNotRegisterd() {
    CmdbCategoryRead<MyCat> read = new CmdbCategoryRead<>(0, MyCat.class);

    assertThatThrownBy(() -> read.getCategory()).isInstanceOf(IllegalStateException.class)
        .hasMessage("Class class com.cargarantie.idoit.api.jsonrpc.CmdbCategoryReadTest$MyCat is not"
            + " registered as a category in class com.cargarantie.idoit.api.model.AllModels");
  }

  @Order(1)
  @Test
  void getCategory_shouldReturnRegisteredName() {
    AllModels.register("CAT_MYCAT", MyCat.class);
    CmdbCategoryRead<MyCat> read = new CmdbCategoryRead<>(0, MyCat.class);

    assertThat(read.getCategory()).isEqualTo("CAT_MYCAT");
  }

  public static class MyCat extends IdoitCategory {

  }
}
