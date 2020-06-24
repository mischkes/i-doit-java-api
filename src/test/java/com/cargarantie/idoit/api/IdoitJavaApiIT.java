package com.cargarantie.idoit.api;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class IdoitJavaApiIT {

  @Order(0)
  void testLogin() {

  }

  @Order(100)
  void testInsertObject() {

  }

  @Order(200)
  void testInsertCategory() {

  }

  @Order(300)
  void testReadStandardCategory() {

  }

  @Order(400)
  void testReadStandard_SimpleNotation() {

  }

  @Order(500)
  void testReadCustomCategory() {

  }

  @Order(600)
  void testUpdateCategory() {

  }

  @Order(700)
  void testReadObject() {

  }

  @Order(800)
  void testUpsertObject() {

  }
}
