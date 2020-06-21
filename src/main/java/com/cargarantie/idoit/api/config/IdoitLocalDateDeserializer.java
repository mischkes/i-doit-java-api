package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.param.IdoitDate;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

//TODO: Convert this to LocalDate
public class IdoitLocalDateDeserializer extends JsonDeserializer<LocalDate> {

  LocalDateDeserializer delegate = new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE);

  @Override
  public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {

    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      IdoitDate date = parser.readValueAs(IdoitDate.class);
      return LocalDate.parse(date.getTitle(), DateTimeFormatter.ISO_DATE);
    } else {
      return delegate.deserialize(parser, ctxt);
    }
  }
}
