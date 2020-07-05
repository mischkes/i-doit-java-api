package com.cargarantie.idoit.api.demo;

import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitObject;
import lombok.Data;

@Data
public class CustomObject extends IdoitObject {
  private CategoryGeneral general;
  private CategoryContactAssignment contactAssignment;
}
