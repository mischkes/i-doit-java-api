package com.cargarantie.idoit.api.jasonrpc;

import lombok.Data;

/*
Example:

{
    "id": 1,
    "jsonrpc": "2.0",
    "result": {
        "id": 215,
        "message": "Category entry successfully created. [This method is deprecated and will be removed in a feature release. Use 'cmdb.category.save' instead.]",
        "success": true
    }
}
 */
@Data
public class CreateResponse {
  private Integer id;
  private String message;
  private boolean success;
}
