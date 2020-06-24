package com.cargarantie.idoit.api;

import java.util.Optional;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class RestClientWrapper {

  private final WebTarget target;
  private MultivaluedMap<String, Object> authHeaders = new MultivaluedHashMap<>();

  public RestClientWrapper(String target) {
    this.target = ClientBuilder.newClient().target(target);
  }

  public void setAuthHeaders(MultivaluedMap<String, Object> authHeaders) {
    this.authHeaders = authHeaders;
  }

  public String post(String json) {
    Entity<String> entity = Entity.json(json);
    Response response = target.request().headers(authHeaders).post(entity);

    if (response.getStatus() != 200) {
      throw new IllegalArgumentException("Request <" + json + "> to target <" + target.getUri()
          + "> returned status <" + response.getStatus() + ">. Full response is <"
          + response.toString() + ">");
    }

    return Optional.ofNullable(response.readEntity(String.class)).orElseThrow(() ->
        new IllegalArgumentException("Request <" + json + "> to target <" + target.getUri()
            + "> did not return content. Maybe the URI is wrong?"));
  }
}
