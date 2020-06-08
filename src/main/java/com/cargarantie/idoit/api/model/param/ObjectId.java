package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value
public class ObjectId {
  private int id;

  @JsonCreator
  public ObjectId(String id) {
    this.id = Integer.parseInt(id);
  }

  public ObjectId(int id) {
    this.id = id;
  }

  public static ObjectId of(int id) {
    return new ObjectId(id);
  }

  @JsonValue
  public int getId() {
    return id;
  }
}
