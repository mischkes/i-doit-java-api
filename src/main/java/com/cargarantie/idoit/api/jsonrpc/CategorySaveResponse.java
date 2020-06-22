package com.cargarantie.idoit.api.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class CategorySaveResponse {
  private int entry;
  private String message;
}
