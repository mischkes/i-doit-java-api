package com.cargarantie.idoit.api.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdoitLocalDateDeserializerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    SimpleModule module = new SimpleModule();
    objectMapper.enable(Feature.ALLOW_SINGLE_QUOTES);
    module.addDeserializer(LocalDate.class, new IdoitLocalDateDeserializer());
    objectMapper.registerModule(module);
  }

  @Test
  void testshouldDeserializeIsoDate() throws IOException {
    LocalDate actual = objectMapper.readValue("'2020-06-24'", LocalDate.class);

    assertThat(actual).isEqualTo(LocalDate.of(2020, 6, 24));
  }

  @Test
  void testDeserializeWrappedIsoDate() throws IOException {
    LocalDate actual = objectMapper.readValue("{'title': '2020-06-24'}", LocalDate.class);

    assertThat(actual).isEqualTo(LocalDate.of(2020, 6, 24));
  }
}
