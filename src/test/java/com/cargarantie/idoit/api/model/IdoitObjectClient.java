package com.cargarantie.idoit.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ObjectTypeName("C__OBJTYPE__CLIENT")
public class IdoitObjectClient extends IdoitObject {

  private CategoryGeneral general;
  private CategoryModel model;

}
