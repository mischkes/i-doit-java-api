package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryCreate;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead.Filter;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead.Ordering;
import com.cargarantie.idoit.api.jsonrpc.CreateResponse;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersion;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersionResponse;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersionResponse.Login;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JsonRpcClient_demoIdoitDeIT {

  private ObjectMapper mapper = IdoitObjectMapper.mapper;
  private JsonRpcClient client;

  @BeforeEach
  void setUp() {
    RestClientWrapper restClient = new RestClientWrapper("https://demo.i-doit.com/src/jsonrpc.php",
        "admin", "admin");
    client = new JsonRpcClient(restClient);
  }

  @Test
  void test_sendVersionRequest() {
    IdoitVersion request = new IdoitVersion();

    IdoitVersionResponse actualResponse = client.send(request);

    IdoitVersionResponse expectedResponse = IdoitVersionResponse.builder().version("1.14.2")
        .step("").type("PRO").login(Login.builder().userid(9).name("i-doit Systemadministrator ")
            .mail("i-doit@acme-it.example").username("admin").tenant("ACME IT Solutions")
            .language("en").build())
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectsReadRequest() {
    CmdbObjectsRead request = CmdbObjectsRead.builder()
        .filter(Filter.builder().type("C__OBJTYPE__CLIENT").build())
        .orderBy(Ordering.title)
        .build();

    ObjectsReadResponse response = client.send(request);

    GeneralObjectData expextedResult = GeneralObjectData.builder().id(5189).title("Laptop")
        .sysid("CLIENT_005189").type(10)
        .created(LocalDateTime.parse("2020-03-18T15:05:42"))
        .updated(LocalDateTime.parse("2020-03-18T15:05:43"))
        .typeTitle("Client").typeGroupTitle("Hardware").status(2).cmdbStatus(6)
        .cmdbStatusTitle("in operation")
        .image("https://demo.i-doit.com/images/objecttypes/empty.png").build();
    assertThat(response.getObjects().get(0)).isEqualTo(expextedResult);
    assertThat(response.getObjects()).hasSize(28);
  }


  @Test
  void test_sendCategoryReadRequest() {
    CmdbCategoryRead<CategoryGeneral> request = new CmdbCategoryRead(1412, CategoryGeneral.class);

    CategoryGeneral actualResponse = client.send(request);

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
  void test_sendCategoryCreateRequest() {
    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(158).role("Administrator").primary("no").build();
    CmdbCategoryCreate request = new CmdbCategoryCreate(contactAssignment);

    CreateResponse response = client.send(request);

    assertThat(response.getMessage()).isEmpty();
    assertThat(response.getId()).isPositive();
  }
}
