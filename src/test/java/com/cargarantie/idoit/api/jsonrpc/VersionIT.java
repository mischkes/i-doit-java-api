package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VersionIT extends IdoitRequestsIT {

  @Test
  void test_sendVersionRequest() {
    mockRestResponse("VersionResponse");

    Version request = new Version();
    VersionResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("Version", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    VersionResponse expectedResponse = VersionResponse.builder().version("1.14.2")
        .step("").type("PRO").login(VersionResponse.Login.builder().userid(9)
            .name("i-doit Systemadministrator ").mail("i-doit@acme-it.example").username("admin")
            .tenant("ACME IT Solutions").language("en").build())
        .build();
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
