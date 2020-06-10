package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.TestUtil.parseJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cargarantie.idoit.api.jsonrpc.CategorySaveResponse;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategorySave;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead.Filter;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead.Ordering;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersion;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersionResponse;
import com.cargarantie.idoit.api.jsonrpc.IdoitVersionResponse.Login;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JsonRpcClientIT {

  private static final String testDataFolder = "json/JsonRpcClientTest/";

  @Mock
  private RestClientWrapper restClient;
  private Object actualRequest;

  private ObjectMapper mapper = IdoitObjectMapper.mapper;
  private JsonRpcClient client;

  void mockRestResponse(String resultFile) {
    when(restClient.post(anyString())).thenAnswer(a -> {
      actualRequest = parseJson(a.getArgument(0, String.class));
      return testData(resultFile);
    });

  }

  @BeforeEach
  void setUp() {
    client = new JsonRpcClient(restClient);
  }

  @Test
  void test_sendVersionRequest() {
    mockRestResponse("VersionResponse");

    IdoitVersion request = new IdoitVersion();
    IdoitVersionResponse actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("IdoitVersion");
    assertThat(actualRequest).isEqualTo(expectedRequest);
    IdoitVersionResponse expectedResponse = IdoitVersionResponse.builder().version("1.14.2")
        .step("").type("PRO").login(Login.builder().userid(9).name("i-doit Systemadministrator ")
            .mail("i-doit@acme-it.example").username("admin").tenant("ACME IT Solutions")
            .language("en").build())
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectCreateRequest() {
    mockRestResponse("ObjectCreateResponse");

    CmdbObjectCreate request = new CmdbObjectCreate("C__OBJTYPE__SERVER", "My little server");
    ObjectCreateResponse actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("CmdbObjectCreate");
    assertThat(actualRequest).isEqualTo(expectedRequest);
    ObjectCreateResponse expectedResponse = new ObjectCreateResponse(5243, "Object was successfully created");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendObjectsReadRequest() {
    mockRestResponse("ObjectsReadResponse");

    CmdbObjectsRead request = CmdbObjectsRead.builder()
        .filter(Filter.builder().type("C__OBJTYPE__CLIENT").build())
        .orderBy(Ordering.title)
        .build();
    ObjectsReadResponse actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("CmdbObjectsRead");
    assertThat(actualRequest).isEqualTo(expectedRequest);
    GeneralObjectData expextedResult = GeneralObjectData.builder().id(5189).title("Laptop")
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
    mockRestResponse("ReadResponse");

    CmdbCategoryRead<CategoryGeneral> request = new CmdbCategoryRead(1412, CategoryGeneral.class);
    CategoryGeneral actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("CmdbCategoryRead");
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
        .contact(158).role("User").primary("no").build();
    CmdbCategorySave request = new CmdbCategorySave(contactAssignment, 871);
    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("CmdbCategorySave_update");
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(871,"Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendCategorySaveRequest_forCreate() {
    mockRestResponse("CategorySaveResponse");

    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(158).role("User").primary("no").build();
    CmdbCategorySave request = new CmdbCategorySave(contactAssignment);
    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = testDataAsMap("CmdbCategorySave_create");
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(871,"Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  private Object testDataAsMap(String fileName) {
    return parseJson(testData(fileName));
  }

  @SneakyThrows
  private InputStream testData(String fileName) {
    return Util.getResource(testDataFolder + fileName + ".json").openStream();
  }
}
