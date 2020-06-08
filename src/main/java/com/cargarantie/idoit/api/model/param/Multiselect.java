package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import java.util.List;
/*
[
  {
    "id": "4",
    "title": "tag1"
  },
  {
    "id": "5",
    "title": "tag2"
  }
]
 */
@Data
@Setter(AccessLevel.PACKAGE)
public class Multiselect {
  List<Element> options;

  @JsonCreator
  public Multiselect(List<Element> options) {
    this.options = options;
  }

  @JsonValue //not yet writeable
  Object outputForm() {
    return null;
  }

  @Data
  public static class Element {
    int id;
    String title;
  }
}
