package com.cargarantie.idoit.api;

import java.io.InputStream;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class RestClientWrapper {
  private final MultivaluedMap<String, Object> authHeaders;
  private final WebTarget target;

  public RestClientWrapper(String target, String username, String password) {
    this.target = ClientBuilder.newClient().target(target);
    authHeaders = new MultivaluedHashMap<>();
    authHeaders.add("X-RPC-Auth-Username", username);
    authHeaders.add("X-RPC-Auth-Password", password);
  }


  public InputStream post(String json) {
    Entity<String> entity = Entity.json(json);
    Response response = target.request()
        .headers(authHeaders)
     .post(entity);

    if (response.getStatus() != 200) {
      //throw some exception
      return null;
    }

    return response.readEntity(InputStream.class);
  }
}
