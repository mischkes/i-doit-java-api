package com.cargarantie.idoit.api.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdoitLocalDateTimeDeserializerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    SimpleModule module = new SimpleModule();
    objectMapper.enable(Feature.ALLOW_SINGLE_QUOTES);
    module.addDeserializer(LocalDateTime.class, new IdoitLocalDateTimeDeserializer());
    objectMapper.registerModule(module);
  }

  @Test
  void deserialize_shouldDeserializeIsoDatetime() throws IOException {
    LocalDateTime actual = objectMapper.readValue("'2020-06-24 12:13:14'", LocalDateTime.class);

    assertThat(actual).isEqualTo(LocalDateTime.of(2020, 6, 24, 12, 13, 14));
  }

  @Test
  void deserialize_shouldDeserializeWrappedIsoDate() throws IOException {
    LocalDateTime actual = objectMapper.readValue(
        "{'title': '2020-06-24', 'prop_type': 'calendar'}", LocalDateTime.class);

    assertThat(actual).isEqualTo(LocalDateTime.of(2020, 6, 24, 0, 0));
  }
}
