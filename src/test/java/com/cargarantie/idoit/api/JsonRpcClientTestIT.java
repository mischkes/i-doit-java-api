package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.TestUtil.parseJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.ReadResponse;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralTest;
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
class JsonRpcClientTestIT {

  private static final String testDataFolder = "json/JsonRpcClientTest/";

  @Mock
  private RestClientWrapper restClient;
  private Object actualRequest;

  private ObjectMapper mapper = IdoitObjectMapper.mapper;
  private JsonRpcClient client;

  void mockRestResult(String resultFile) {
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
  void test_sendCategoryReadRequest() {
    mockRestResult("ReadResponse");

    CmdbCategoryRead<CategoryGeneral> request = new CmdbCategoryRead(1412, CategoryGeneral.class);
    ReadResponse<CategoryGeneral> actualResult = client.send(request);

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
    assertThat(actualResult.getResult()).isEqualTo(expectedResult);
  }

  private Object testDataAsMap(String fileName) {
    return parseJson(testData(fileName));
  }

  @SneakyThrows
  private InputStream testData(String fileName) {
    return Util.getResource(testDataFolder + fileName + ".json").openStream();
  }
}
