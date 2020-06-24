package com.cargarantie.idoit.api.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//TODO: Convert this to LocalDate
class IdoitLocalDateDeserializer extends JsonDeserializer<LocalDate> {

  LocalDateDeserializer delegate = new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE);

  @Override
  public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {

    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      IdoitDate date = parser.readValueAs(IdoitDate.class);
      return LocalDate.parse(date.getTitle(), DateTimeFormatter.ISO_DATE);
    } else {
      return delegate.deserialize(parser, ctxt);
    }
  }

}
