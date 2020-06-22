package com.cargarantie.idoit.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdoitObjectClient extends IdoitObject {

  private CategoryGeneral general;
  private IdoitCategoryClientDescription description;
  private CategoryModel model;

}
