package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import com.cargarantie.idoit.api.model.param.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectCreateIT extends IdoitRequestsIT {

  @Test
  void test_sendObjectCreateRequest() {
    mockRestResponse("ObjectCreateResponse");
    ObjectCreate request = new ObjectCreate("C__OBJTYPE__SERVER", "My little server");

    ObjectCreateResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("ObjectCreate", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    ObjectCreateResponse expectedResponse = new ObjectCreateResponse(ObjectId.of(5243),
        "Object was successfully created");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
