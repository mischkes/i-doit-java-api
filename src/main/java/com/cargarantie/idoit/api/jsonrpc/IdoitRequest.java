package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class IdoitRequest<T> {
  @JsonProperty("apikey")
  private String apiKey; //this variable is set by the Session object

  @JsonIgnore
  public abstract Class<T> getResponseClass();
  @JsonIgnore
  public abstract String getMethod();
}
