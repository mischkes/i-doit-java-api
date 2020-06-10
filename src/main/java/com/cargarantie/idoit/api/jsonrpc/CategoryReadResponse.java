package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.IdoitObjectMapper;
import com.cargarantie.idoit.api.model.IdoitCategory;
import java.util.List;
import java.util.Map;
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
public class CategoryReadResponse<T extends IdoitCategory> extends IdoitResponse {
  private T result;
}
