package com.cargarantie.idoit.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

//TODO: Convert this to LocalDate
public class IdoitLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  LocalDateTimeDeserializer delegate = new LocalDateTimeDeserializer(formatter);

  public IdoitLocalDateTimeDeserializer(DateTimeFormatter formatter) {

    this.formatter = formatter;
  }

  @Data
  static class WrappedDatetime {
     String title;
     String prop_type;
  }

  @Override
  public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      WrappedDatetime wrappedDatetime = parser.readValueAs(WrappedDatetime.class);
      return LocalDate.parse(wrappedDatetime.title, DateTimeFormatter.ISO_DATE).atStartOfDay();
    } else {
      return delegate.deserialize(parser, ctxt);
    }
  }
}
