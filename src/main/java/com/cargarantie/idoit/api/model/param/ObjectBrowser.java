package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;

/**
 * Example:
 * <pre>
 * {
 *   "id": "100",
 *   "title": "CMDB Object #100",
 *   "sysid": "SYSID_1280838789",
 *   "type": "C__OBJTYPE__SERVER",
 *   "type_title": "LC__CMDB__OBJTYPE__SERVER"
 * }
 * </pre>
 */
@Value
@Builder
public class ObjectBrowser {

  @JsonValue
  ObjectId id; //only the id is used to update entries

  String title;
  String sysid;
  String type;
  String typeTitle;

  public static ObjectBrowser of(ObjectId id) {
    return ObjectBrowser.builder().id(id).build();
  }

  public static ObjectBrowser of(int id) {
    return of(new ObjectId(id));
  }
}
