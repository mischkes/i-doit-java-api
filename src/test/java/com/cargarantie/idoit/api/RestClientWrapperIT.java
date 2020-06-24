package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RestClientWrapperIT {

  @Test
  void post_shouldThrowException_whenServerDoesNotReturn200() {
    RestClientWrapper wrapper = new RestClientWrapper(
        "https://demo.i-doit.com/src/jsonrpc2.php");

    assertThatThrownBy(() -> wrapper.post("some request"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Request <some request> to target <https://demo.i-doit.com/src/jsonrpc2.php>"
            + " returned status <403>. Full response is <InboundJaxrsResponse{ClientResponse"
            + "{method=POST, uri=https://demo.i-doit.com/src/jsonrpc2.php,"
            + " status=403, reason=Forbidden}}>");
  }
}
