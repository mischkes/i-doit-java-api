package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.model.param.ObjectId.of;
import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.cargarantie.idoit.api.jsonrpc.CategorySaveResponse;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead.Ordering;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.Login;
import com.cargarantie.idoit.api.jsonrpc.Version;
import com.cargarantie.idoit.api.jsonrpc.VersionResponse;
import com.cargarantie.idoit.api.jsonrpc.LoginResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * This test tests directly against demo.i-doit.de It might turn out to fragile, but it can provide
 * the most confidence that everything is correct.
 */
@ExtendWith(MockitoExtension.class)
class JsonRpcClient_demoIdoitDe_IT {

  private ObjectMapper mapper = IdoitObjectMapper.mapper;
  private JsonRpcClient client;

  @BeforeEach
  void setUp() {
    RestClientWrapper restClient = new RestClientWrapper("https://demo.i-doit.com/src/jsonrpc.php");
    client = new JsonRpcClient(restClient, "c1ia5q");
    client.login("admin", "admin");
  }

  @Test
  void test_sendVersionRequest() {
    Version request = new Version();

    VersionResponse actualResponse = client.send(request);

    VersionResponse expectedResponse = VersionResponse.builder().version("1.14.2")
        .step("").type("PRO").login(VersionResponse.Login.builder().userid(9).name("i-doit Systemadministrator ")
            .mail("i-doit@acme-it.example").username("admin").tenant("ACME IT Solutions")
            .language("en").build())
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendLogin() {
    Login request = new Login();

    LoginResponse actualResponse = client.send(request);

    /*
    {"result":true,"userid":"9","name":"i-doit Systemadministrator ","mail":"i-doit@acme-it.example","username":"admin","session-id":"3ro0o7ceh24rm1ci241992i7f3","client-id":1,"client-name":"ACME IT Solutions"}
     */
    //TODO
    assertThat(actualResponse).isEqualTo(null);
  }

  @Test
  void test_sendObjectCreateRequest() {
    ObjectCreate request = new ObjectCreate("C__OBJTYPE__SERVER", "My little server");

    ObjectCreateResponse response = client.send(request);

    assertThat(response.getId()).isGreaterThan(0);
    assertThat(response.getMessage()).isEqualTo("Object was successfully created");
  }

  @Test
  void test_sendObjectsReadRequest() {
    ObjectsRead<?> request = ObjectsRead.builder().filterTypeName("C__OBJTYPE__CLIENT")
        .orderBy(Ordering.title).build();

    ObjectsReadResponse response = client.send(request);

    GeneralObjectData expextedResult = GeneralObjectData.builder().id(of(5189)).title("Laptop")
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
    CategoryRead<CategoryGeneral> request = new CategoryRead(of(1412), CategoryGeneral.class);

    CategoryGeneral actualResponse = client.send(request);

    CategoryGeneral expectedResult = CategoryGeneral.builder().id(CategoryId.of(1412))
        .objId((of(1412))).title("Laptop 001")
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
  void test_sendCategorySaveRequest_forUpdate() {
    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(of(1412))
        .contact(158).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment, 868);

    CategorySaveResponse response = client.send(request);

    assertThat(response.getMessage()).matches("Category entry successfully saved");
    assertThat(response.getEntry()).isEqualTo(868);
  }

  @Test
  void test_sendCategorySaveRequest_forCreate() {
    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(of(1412))
        .contact(158).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment);

    CategorySaveResponse response = client.send(request);

    assertThat(response.getMessage()).matches("Category entry successfully saved");
    assertThat(response.getEntry()).isGreaterThan(868);
  }
}
