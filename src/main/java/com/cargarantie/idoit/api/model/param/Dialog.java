package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
{
  "id": "1",
  "title": "In Betrieb",
  "const": "C__DIALOG_CONSTANT__IN_OPERATION",
  "title_lang": "LC__DIALOG_ENTRY__IN_OPERATION"
}

//dialog plus - allows addition of new constants
//addtion - by title or by constant?

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PACKAGE)
public class Dialog {
  int id;
  String title;
  String titleLang;

  @JsonProperty("const")
  String constant;

  @JsonValue
  Object jsonValue() {
    return id != 0 ? id : constant != null ? constant : title;
  }

  public static Dialog fromId(int id) {
    return Dialog.builder().id(id).build();
  }

  public static Dialog fromTitle(String title) {
    return Dialog.builder().title(title).build();
  }

  public static Dialog fromConstant(String constant) {
    return Dialog.builder().constant(constant).build();
  }
}
