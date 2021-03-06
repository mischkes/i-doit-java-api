package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.model.annotation.CategoryName;
import com.cargarantie.idoit.api.model.param.ObjectBrowser;
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
@CategoryName("C__CATG__CONTACT")
public class CategoryContactAssignment extends IdoitCategory {

  private ObjectBrowser contact;
  private String role;
  private String primary;
}
