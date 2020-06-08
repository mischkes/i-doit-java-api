package com.cargarantie.idoit.api.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class IdoitRequest<T> {
  private String apiKey; //this variable is set by the Session object

  public abstract Class<T> getResponseClass();
  public String getMethod() {
    return camelToDnsCase(getClass().getSimpleName());
  }

  private static String camelToDnsCase(String str) {
    return str.replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
  }
}
