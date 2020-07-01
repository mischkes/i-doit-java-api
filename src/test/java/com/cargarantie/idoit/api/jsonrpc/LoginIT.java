package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginIT extends IdoitRequestsIT {

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
}
