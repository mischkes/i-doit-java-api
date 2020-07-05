package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead.Ordering;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectsReadIT extends IdoitRequestsIT {

  @Test
  void test_sendObjectsReadRequest() {
    mockRestResponse("ObjectsReadResponse");
    ObjectsRead<?> request = ObjectsRead.builder().filterTypeName("C__OBJTYPE__CLIENT")
        .orderBy(Ordering.TITLE).build();

    ObjectsReadResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("ObjectsRead", Object.class);
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
}
