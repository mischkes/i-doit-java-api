package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResult;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class CategoryGeneralTest {
  @Test
  void testCategoryName() {
    //Assertions.assertThat(new CategoryGeneral().categoryName()).isEqualTo("C__CATG__GLOBAL");
  }

  @Test
  void constructCatGeneral(){
    //new CategoryGeneral().setCre
    CategoryGeneral.builder().category(Dialog.fromTitle("Other"));
  }

  @Test
  void testReadJson() throws IOException {
    ObjectMapper mapper = idoitMapper();

    JsonRpcResult rpcResult = mapper
        .readValue(Util.getResource("json/categoryGeneralRead.json"), JsonRpcResult.class);
    CategoryGeneral general = mapper.convertValue(rpcResult.getResult().get(0), CategoryGeneral.class);

    System.out.println(general);

    System.out.println(mapper.writeValueAsString(general));
  }

  public ObjectMapper idoitMapper() {
    ObjectMapper mapper = new ObjectMapper();

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.registerModule(idoitMapperJavaTimeModule());

    return mapper;
  }

  public JavaTimeModule idoitMapperJavaTimeModule() {
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));

    return javaTimeModule;
  }


}
