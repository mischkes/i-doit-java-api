package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.model.param.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectDeleteIT extends IdoitRequestsIT {

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
