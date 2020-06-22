package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

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
@Value
@Builder
public class GeneralObjectData {
  ObjectId id;
  String title;
  String sysid;
  int type;
  LocalDateTime created;
  LocalDateTime updated;
  String typeTitle;
  String typeGroupTitle;
  int status;
  int cmdbStatus;
  String cmdbStatusTitle;
  String image;
}
