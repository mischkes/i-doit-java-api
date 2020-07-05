package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

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
