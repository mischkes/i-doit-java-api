package com.cargarantie.idoit.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@CategoryName("C__CATG__MODEL")
public class CategoryModel extends IdoitCategory {

  private String manufacturer;
  private String title;
  private String status;
  private String productid;
  private String serviceTag;
  private String serial;
  private String firmware;
  private String description;
}
