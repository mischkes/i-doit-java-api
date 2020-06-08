package com.cargarantie.idoit.api.model.param;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
{
  "id": "100",
  "title": "CMDB Object #100",
  "sysid": "SYSID_1280838789",
  "type": "C__OBJTYPE__SERVER",
  "type_title": "Server",
  //below seems proprietary
  "logPortId": "1",
  "logPortTitle": "Logical port 1"
}

{
  "id": "100",
  "title": "Cmdb object 1",
  "cable_id": "50",
  "sysid": "SYSID_1280838789",
  "type": "C__OBJTYPE__SERVER",
  "name": "Connector 1",
  "assigned_category": "@todo",
  "connector_type": "1",
  "con_type": "RJ-45"
}

Out
Integer

 */
/* Example: ContactAssignment.contact_object
this seems to be the default
{
  "id": "100",
  "title": "CMDB Object #100",
  "sysid": "SYSID_1280838789",
  "type": "C__OBJTYPE__SERVER",
  "type_title": "LC__CMDB__OBJTYPE__SERVER",
  "connection_id": "50"
}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PACKAGE)
public class ObjectBrowser {
  @JsonValue //only the id is used to update entries
  ObjectId id;
  String title;
  String sysid;
  String type;
  String typeTitle;

  public static ObjectBrowser of(ObjectId id) {
    ObjectBrowser o = new ObjectBrowser();
    o.setId(id);
    return o;
  }

  public static ObjectBrowser of(int id) {
    return of(new ObjectId(id));
  }
}
