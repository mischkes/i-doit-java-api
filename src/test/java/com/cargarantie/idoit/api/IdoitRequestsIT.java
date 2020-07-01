package com.cargarantie.idoit.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class IdoitRequestsIT extends TestResourceAccess {

  protected Object actualRequest;
  protected JsonRpcClient client;

  @Mock
  private RestClientWrapper restClient;

  protected void mockRestResponse(String resultFile) {
    when(restClient.post(anyString())).thenAnswer(a -> {
      actualRequest = parseJson(a.getArgument(0, String.class));
      return getJsonString(resultFile);
    });
  }

  @BeforeEach
  void setUp() {
    client = new JsonRpcClient(restClient, "c1ia5q");
  }
}
