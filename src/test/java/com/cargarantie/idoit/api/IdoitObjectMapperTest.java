package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.IdoitObjectMapper.mapper;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParser.Feature;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class IdoitObjectMapperTest {

  @SneakyThrows
  @Test
  void parser_shouldParseObjectWrappedDatetimeCorrectly() {
    String data = "{ 'title': '2020-07-03', 'prop_type': 'calendar'}";
    mapper.enable(Feature.ALLOW_SINGLE_QUOTES);

    LocalDateTime actual = mapper.readValue(data, LocalDateTime.class);

    assertThat(actual).isEqualTo("2020-07-03T00:00");
  }
}
