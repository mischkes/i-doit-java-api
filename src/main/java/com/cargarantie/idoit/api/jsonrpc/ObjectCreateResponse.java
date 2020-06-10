package com.cargarantie.idoit.api.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectCreateResponse {
  private int id;
  private String message;
}
