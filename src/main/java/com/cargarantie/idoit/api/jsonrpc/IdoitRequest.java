package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IdoitRequest<T> {

  ObjectMapper mapper = IdoitObjectMapper.getObjectMapper();

  @JsonIgnore
  Class<T> getResponseClass();

  @JsonIgnore
  default T mapResponse(Object data) {
    return mapper.convertValue(data, getResponseClass());
  }

  @JsonIgnore
  String getMethod();
}
