package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.param.IdoitDate;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdoitLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  public final static DateTimeFormatter IDOIT_DATE_FORMAT = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");
  LocalDateTimeDeserializer delegate = new LocalDateTimeDeserializer(IDOIT_DATE_FORMAT);

  @Override
  public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {

    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      IdoitDate idoitDate = parser.readValueAs(IdoitDate.class);
      return LocalDate.parse(idoitDate.getTitle(), DateTimeFormatter.ISO_DATE).atStartOfDay();
    } else {
      return delegate.deserialize(parser, ctxt);
    }
  }

}
