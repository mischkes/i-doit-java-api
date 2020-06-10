package com.cargarantie.idoit.api.jsonrpc;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralObjectData {
  private int id;
  private String title;
  private String sysid;
  private int type;
  private LocalDateTime created;
  private LocalDateTime updated;
  private String typeTitle;
  private String typeGroupTitle;
  private int status;
  private int cmdbStatus;
  private String cmdbStatusTitle;
  private String image;
}
