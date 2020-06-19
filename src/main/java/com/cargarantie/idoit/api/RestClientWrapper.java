package com.cargarantie.idoit.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class RestClientWrapper {
  private MultivaluedMap<String, Object> authHeaders = new MultivaluedHashMap<>();
  private final WebTarget target;

  public RestClientWrapper(String target) {
    this.target = ClientBuilder.newClient().target(target);
  }

  public void setAuthHeaders(MultivaluedMap<String, Object> authHeaders) {
    this.authHeaders = authHeaders;
  }

  public String post(String json) {
    Entity<String> entity = Entity.json(json);
    Response response = target.request()
        .headers(authHeaders)
     .post(entity);

    if (response.getStatus() != 200) {
      //throw some exception
      return null;
    }

    return response.readEntity(String.class);
  }
}
