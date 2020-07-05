package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.CategoryId;
import lombok.Value;

@Value
public class CategorySaveResponse {

  CategoryId entry;
  String message;
}
