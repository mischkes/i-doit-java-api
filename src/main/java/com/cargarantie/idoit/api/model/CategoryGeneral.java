package com.cargarantie.idoit.api.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@IdoitCategoryName("C__CATG__GLOBAL")
public class CategoryGeneral extends IdoitCategory {

  private String cmdbStatus;
  private String sysid;
  private LocalDateTime created;
  private LocalDateTime changed;
  private String category;
  private String purpose;
  private String status;
  private String title;
}
