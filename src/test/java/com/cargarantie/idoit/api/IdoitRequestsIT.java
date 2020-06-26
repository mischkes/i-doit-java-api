package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.CategorySaveResponse;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.Login;
import com.cargarantie.idoit.api.jsonrpc.LoginResponse;
import com.cargarantie.idoit.api.jsonrpc.Logout;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead.Ordering;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.jsonrpc.SimpleSuccessResponse;
import com.cargarantie.idoit.api.jsonrpc.Version;
import com.cargarantie.idoit.api.jsonrpc.VersionResponse;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectBrowser;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdoitRequestsIT extends TestResourceAccess {

  @Mock
  private RestClientWrapper restClient;
  private Object actualRequest;

  private JsonRpcClient client;

  void mockRestResponse(String resultFile) {
    when(restClient.post(anyString())).thenAnswer(a -> {
      actualRequest = parseJson(a.getArgument(0, String.class));
      return getJson(resultFile);
    });

  }

  @BeforeEach
  void setUp() {
    client = new JsonRpcClient(restClient, "c1ia5q");
  }

  @Test
  void test_sendLoginRequest() {
    mockRestResponse("LoginResponse");
    Login request = new Login();

    LoginResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("Login", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    LoginResponse expectedResponse = LoginResponse.builder().userid("9")
        .name("i-doit Systemadministrator ").mail("i-doit@acme-it.example").username("admin")
        .sessionId("37afuc8qfh6r6lgo5sc47gtgj7").clientId(1).clientName("ACME IT Solutions")
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendLogoutRequest() {
    mockRestResponse("LogoutResponse");

    Logout request = new Logout();
    SimpleSuccessResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("Logout", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    SimpleSuccessResponse expectedResponse = new SimpleSuccessResponse("Logout successfull");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendVersionRequest() {
    mockRestResponse("VersionResponse");

    Version request = new Version();
    VersionResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("IdoitVersion", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    VersionResponse expectedResponse = VersionResponse.builder().version("1.14.2")
        .step("").type("PRO").login(VersionResponse.Login.builder().userid(9)
            .name("i-doit Systemadministrator ").mail("i-doit@acme-it.example").username("admin")
            .tenant("ACME IT Solutions").language("en").build())
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectCreateRequest() {
    mockRestResponse("ObjectCreateResponse");

    ObjectCreate request = new ObjectCreate("C__OBJTYPE__SERVER", "My little server");
    ObjectCreateResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CmdbObjectCreate", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    ObjectCreateResponse expectedResponse = new ObjectCreateResponse(ObjectId.of(5243),
        "Object was successfully created");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectsReadRequest() {
    mockRestResponse("ObjectsReadResponse");

    ObjectsRead<?> request = ObjectsRead.builder().filterTypeName("C__OBJTYPE__CLIENT")
        .orderBy(Ordering.TITLE).build();
    ObjectsReadResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CmdbObjectsRead", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    GeneralObjectData expextedResult = GeneralObjectData.builder().id(ObjectId.of(5189))
        .title("Laptop")
        .sysid("CLIENT_005189").type(10)
        .created(LocalDateTime.parse("2020-03-18T15:05:42"))
        .updated(LocalDateTime.parse("2020-03-18T15:05:43"))
        .typeTitle("Client").typeGroupTitle("Hardware").status(2).cmdbStatus(6)
        .cmdbStatusTitle("in operation")
        .image("https://demo.i-doit.com/images/objecttypes/empty.png").build();
    assertThat(actualResponse.getObjects().get(0)).isEqualTo(expextedResult);
    assertThat(actualResponse.getObjects()).hasSize(28);
  }

  @Test
  void test_sendCategoryReadRequest() {
    System.out.println(Arrays.stream("".split(",")).collect(Collectors.toSet()).size());

    mockRestResponse("ReadResponse");

    final CategoryRead<CategoryGeneral> request = new CategoryRead(ObjectId.of(1412),
        CategoryGeneral.class);
    final CategoryGeneral actualResponse = client.send(request);

    Object expectedRequest = getJson("CmdbCategoryRead", Object.class);
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
  void test_sendCategorySave_forUpdate() {
    mockRestResponse("CategorySaveResponse");

    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(ObjectBrowser.of(158)).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment, 871);
    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CmdbCategorySave_update", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(actualResponse.getEntry(),
        "Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendCategorySaveRequest_forCreate() {
    mockRestResponse("CategorySaveResponse");

    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(ObjectBrowser.of(158)).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment);
    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CmdbCategorySave_create", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(actualResponse.getEntry(),
        "Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectDelete() {
    mockRestResponse("ObjectDeleteResponse");

    ObjectDelete request = new ObjectDelete(ObjectId.of(1), DeleteAction.DELETE);
    SimpleSuccessResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("ObjectDelete", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    SimpleSuccessResponse expectedResponse = new SimpleSuccessResponse(
        "Object 1 has been deleted.");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
