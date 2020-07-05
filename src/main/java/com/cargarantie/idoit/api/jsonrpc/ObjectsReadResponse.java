package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Value;

@Value
public class ObjectsReadResponse {

  List<GeneralObjectData> objects;

  @JsonCreator
  public ObjectsReadResponse(List<GeneralObjectData> objects) {
    this.objects = objects;
  }

}
