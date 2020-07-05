package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
import lombok.Value;

@Value
public class ObjectCreateResponse {

  ObjectId id;
  String message;
}
