package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Data;

/*
id	String	Object identifier (as numeric string)
title	String	Object title
sysid	String	SYSID (see category Global)
type	String	Object type identifier (as numeric string)
created	String	Date of creation; format: Y-m-d H:i:s
updated	String

Date of last update; format: Y-m-d H:i:s

Note: This key is optional because not every object has been updated before.
type_title	String	Translated name of object type
type_group_title	String	Translated name of object type group
status	String

Object status (as numeric string):

    "1": Unfinished
    "2": Normal
    "3": Archived
    "4": Deleted
    "6": Template
    "7": Mass change template

cmdb_status	String	CMDB status (see category Global; as numeric string)
cmdb_status_title	String	Translated CMDB status (see category Global)
image	String	URL to object picture
 */
@Data
public class ObjectsReadResponse {
  private List<GeneralObjectData> objects;

  @JsonCreator
  public ObjectsReadResponse(List<GeneralObjectData> objects) {
    this.objects = objects;
  }

}
