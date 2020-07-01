package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogoutIT extends IdoitRequestsIT {

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
}
