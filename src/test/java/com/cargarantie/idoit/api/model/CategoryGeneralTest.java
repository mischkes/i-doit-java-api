package com.cargarantie.idoit.api.model;

import static com.cargarantie.idoit.api.TestUtil.parseJson;
import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitObjectMapper;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResult;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class CategoryGeneralTest {
  ObjectMapper mapper = IdoitObjectMapper.mapper;

  @Test
  void testReadJson() throws IOException {
    CategoryGeneral g = loadCategory();

    assertThat(g.getStatus()).isEqualTo(new Dialog(2, "Normal","LC__CMDB__RECORD_STATUS__NORMAL", ""));
    assertThat(g.getCreated()).isEqualTo("2015-05-04T18:02:10");
    assertThat(g.getId()).isEqualTo(CategoryId.of(1412));
    assertThat(g.getObjId()).isEqualTo(ObjectId.of(1412));
  }

  private CategoryGeneral loadCategory() throws IOException {
    JsonRpcResult rpcResult = mapper
        .readValue(Util.getResourceAsString("json/categoryGeneralRead.json"), JsonRpcResult.class);
    return mapper.convertValue(((List<Object>) (rpcResult.getResult())).get(0), CategoryGeneral.class);
  }

  @Test
  void writeJson_shouldWriteCorrectJsonString() throws IOException {
    CategoryGeneral g =  loadCategory();

    String actual = mapper.writeValueAsString(g);

    String expected = "{'description':'<p>some description</p>','title':'Laptop 001','status':2,'purpose':1,'category':2,'sysid':'CLIENT_001412','cmdb_status':6,'type':10,'tag':null}";
    assertThat(parseJson(actual)).isEqualTo(parseJson(expected));
    System.out.println(actual);

  }

  public static void setCreatedData(CategoryGeneral category, LocalDateTime createdAt, String createdBy) {
    category.setCreated(createdAt);
    category.setCreatedBy(createdBy);
  }

  public static void setChangedData(CategoryGeneral category, LocalDateTime changedAt, String changedBy) {
    category.setChanged(changedAt);
    category.setChangedBy(changedBy);
  }
}
