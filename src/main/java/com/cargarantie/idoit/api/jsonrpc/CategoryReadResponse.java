package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitCategory;
import lombok.Value;

@Value
public class CategoryReadResponse<T extends IdoitCategory> {
  int id;
  T result;
}
