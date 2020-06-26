package com.cargarantie.idoit.api.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IdoitObjectMapper {

  private static final ObjectMapper mapper = newObjectMapper();

  public static ObjectMapper getObjectMapper() {
    return mapper;
  }

  public static ObjectMapper newObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.registerModule(idoitMapperJavaTimeModule());
    mapper.registerModule(idoitFieldsModule());

    return mapper;
  }

  private static SimpleModule idoitFieldsModule() {
    SimpleModule idoitFieldsModule = new SimpleModule();
    idoitFieldsModule.addDeserializer(String.class, new IdoitStringDeserializer());

    return idoitFieldsModule;
  }

  private static JavaTimeModule idoitMapperJavaTimeModule() {
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addDeserializer(LocalDateTime.class, new IdoitLocalDateTimeDeserializer());
    javaTimeModule.addDeserializer(LocalDate.class, new IdoitLocalDateDeserializer());
    javaTimeModule.addSerializer(LocalDateTime.class,
        new LocalDateTimeSerializer(IdoitLocalDateTimeDeserializer.IDOIT_DATE_TIME));
    javaTimeModule.addSerializer(LocalDate.class,
        new LocalDateSerializer(IdoitLocalDateDeserializer.IDOIT_DATE));

    return javaTimeModule;
  }
}
