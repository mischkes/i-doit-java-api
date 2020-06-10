package com.cargarantie.idoit.api.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveResponse {
  private int entry;
  private String message;
}
