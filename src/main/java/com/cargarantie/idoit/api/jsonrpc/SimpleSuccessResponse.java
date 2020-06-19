package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;

@Data
public class SimpleSuccessResponse {
  private String message;
  private boolean success;
}
