package com.cargarantie.idoit.api.demo;

import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.IdoitObjectName;
import lombok.Data;

@IdoitObjectName("DemoObject")
@Data
public class DemoObject extends IdoitObject {
  private CategoryGeneral general;
  private CategoryContactAssignment contactAssignment;
}
