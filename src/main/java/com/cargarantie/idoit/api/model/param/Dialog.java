package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Example:
 * <pre>
 * {
 *   "id": "1",
 *   "title": "In Betrieb",
 *   "const": "C__DIALOG_CONSTANT__IN_OPERATION",
 *   "title_lang": "LC__DIALOG_ENTRY__IN_OPERATION"
 * }
 * </pre>
 */
@Value
@Builder
@AllArgsConstructor
public class Dialog {

  int id;
  String title;
  String titleLang;

  @JsonProperty("const")
  String constant;

  public static Dialog fromId(int id) {
    return Dialog.builder().id(id).build();
  }

  public static Dialog fromTitle(String title) {
    return Dialog.builder().title(title).build();
  }

  public static Dialog fromConstant(String constant) {
    return Dialog.builder().constant(constant).build();
  }

  @JsonValue
  Object jsonValue() {
    return id != 0 ? id : constant != null ? constant : title;
  }
}
