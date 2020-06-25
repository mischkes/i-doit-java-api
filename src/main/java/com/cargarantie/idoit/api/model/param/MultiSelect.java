package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.Value;

@Value
public class MultiSelect {

  List<Element> options;

  @JsonCreator
  public MultiSelect(List<Element> options) {
    this.options = options;
  }

  /**
   * MultiSelects are not yet writeable
   */
  @JsonValue
  public Object jsonValue() {
    return null;
  }

  @Value
  public static class Element {

    int id;
    String title;
  }
}
