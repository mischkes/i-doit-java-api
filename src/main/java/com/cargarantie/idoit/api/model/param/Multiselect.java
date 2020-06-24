package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.Value;

@Value
public class Multiselect {

  List<Element> options;

  @JsonCreator
  public Multiselect(List<Element> options) {
    this.options = options;
  }

  @JsonValue
  /**
   * Not yet writeable
   */
  public Object jsonValue() {
    return null;
  }

  @Value
  public static class Element {

    int id;
    String title;
  }
}
