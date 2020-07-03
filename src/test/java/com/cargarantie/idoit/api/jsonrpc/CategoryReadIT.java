package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.annotation.CategoryName;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryReadIT extends IdoitRequestsIT {

  @Test
  void test_sendReadRequest_standardCategory() {
    mockRestResponse("CategoryReadResponse");
    final CategoryRead<CategoryGeneral> request = new CategoryRead(ObjectId.of(1412),
        CategoryGeneral.class);

    final CategoryGeneral actualResponse = client.send(request);

    Object expectedRequest = getJson("CategoryRead", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategoryGeneral expectedResult = CategoryGeneral.builder().id(CategoryId.of(1412))
        .objId((ObjectId.of(1412))).title("Laptop 001")
        .status(new Dialog(2, "Normal", "LC__CMDB__RECORD_STATUS__NORMAL", ""))
        .purpose(new Dialog(1, "Production", "LC__CMDB__CATG__PURPOSE_PRODUCTION", null))
        .category(new Dialog(2, "Demo", "Demo", null))
        .cmdbStatus(new Dialog(6, "in operation", "LC__CMDB_STATUS__IN_OPERATION",
            "C__CMDB_STATUS__IN_OPERATION"))
        .type(new Dialog(10, "Client", "LC__CMDB__OBJTYPE__CLIENT", "C__OBJTYPE__CLIENT"))
        .sysid("CLIENT_001412").tag(null).description("")
        .build();
    CategoryGeneralTest
        .setCreatedData(expectedResult, LocalDateTime.parse("2015-05-04T18:02:10"), "admin");
    CategoryGeneralTest
        .setChangedData(expectedResult, LocalDateTime.parse("2017-07-03T14:54:59"), "admin");
    assertThat(actualResponse).isEqualTo(expectedResult);
  }

  @Test
  void test_sendReadRequest_customCategory() {
    mockRestResponse("CategoryReadResponseCustomCategory");
    CategoryRead<CustomCategory> request = new CategoryRead<>(ObjectId.of(0), CustomCategory.class);

    final CustomCategory actualResponse = client.send(request);

    CustomCategory expectedResponse = CustomCategory.builder().id(CategoryId.of(821))
        .objId(ObjectId.of(11457)).form(Dialog.fromTitle("a form")).os(null)
        .simpleString("a simple string").description("").build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @SuperBuilder
  @EqualsAndHashCode(callSuper = true)
  @ToString(callSuper = true)
  @CategoryName("CustomCategory")
  public static class CustomCategory extends IdoitCategory {

    private Dialog form;
    private Dialog os;
    private String simpleString;
  }
}
