package com.cargarantie.idoit.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.TestResourceAccess;
import com.cargarantie.idoit.api.jsonrpc.JsonRpcResponse;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CategoryGeneralTest extends TestResourceAccess {

  public static void setCreatedData(CategoryGeneral category, LocalDateTime createdAt,
      String createdBy) {
    category.setCreated(createdAt);
    category.setCreatedBy(createdBy);
  }

  public static void setChangedData(CategoryGeneral category, LocalDateTime changedAt,
      String changedBy) {
    category.setChanged(changedAt);
    category.setChangedBy(changedBy);
  }

  @Test
  void testReadJson() {
    CategoryGeneral general = loadCategory();

    assertThat(general.getStatus())
        .isEqualTo(new Dialog(2, "Normal", "LC__CMDB__RECORD_STATUS__NORMAL", ""));
    assertThat(general.getCreated()).isEqualTo("2015-05-04T18:02:10");
    assertThat(general.getId()).isEqualTo(CategoryId.of(1412));
    assertThat(general.getObjId()).isEqualTo(ObjectId.of(1412));
  }

  @Test
  void writeJson_shouldWriteCorrectJsonString() throws IOException {
    CategoryGeneral general = loadCategory();

    String actual = mapper.writeValueAsString(general);

    String expected = "{'description':'<p>some description</p>','title':'Laptop 001','status':2,'purpose':1,'category':2,'sysid':'CLIENT_001412','cmdb_status':6,'type':10,'tag':null}";
    assertThat(parseJson(actual)).isEqualTo(parseJson(expected));
  }

  private CategoryGeneral loadCategory() {
    JsonRpcResponse rpcResult = getJson("categoryGeneralRead", JsonRpcResponse.class);
    return mapper
        .convertValue(((List<Object>) (rpcResult.getResult())).get(0), CategoryGeneral.class);
  }
}
