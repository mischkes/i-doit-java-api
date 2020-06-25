package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.TestResourceAccess;
import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResponse;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CategoryGeneralTest extends TestResourceAccess {

  private ObjectMapper mapper = IdoitObjectMapper.mapper;

  @Test
  void testReadJson() throws IOException {
    CategoryGeneral g = loadCategory();

    assertThat(g.getStatus())
        .isEqualTo(new Dialog(2, "Normal", "LC__CMDB__RECORD_STATUS__NORMAL", ""));
    assertThat(g.getCreated()).isEqualTo("2015-05-04T18:02:10");
    assertThat(g.getId()).isEqualTo(CategoryId.of(1412));
    assertThat(g.getObjId()).isEqualTo(ObjectId.of(1412));
  }

  private CategoryGeneral loadCategory() {
    JsonRpcResponse rpcResult = getJson("categoryGeneralRead", JsonRpcResponse.class);
    return mapper
        .convertValue(((List<Object>) (rpcResult.getResult())).get(0), CategoryGeneral.class);
  }

  @Test
  void writeJson_shouldWriteCorrectJsonString() throws IOException {
    CategoryGeneral g = loadCategory();

    String actual = mapper.writeValueAsString(g);

    String expected = "{'description':'<p>some description</p>','title':'Laptop 001','status':2,'purpose':1,'category':2,'sysid':'CLIENT_001412','cmdb_status':6,'type':10,'tag':null}";
    assertThat(parseJson(actual)).isEqualTo(parseJson(expected));
  }
}
