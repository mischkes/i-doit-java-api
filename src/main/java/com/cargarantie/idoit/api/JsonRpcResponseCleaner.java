package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonRpcResponseCleaner {

  private final ObjectMapper mapper;

  public JsonRpcResponseCleaner(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public <T> T cleanResult(IdoitRequest<T> request, Object result) {

    if (request instanceof CategoryRead) {
      // CmdbCategoryRead only parses non-multi list. Then, result is always a list of 1.
      List<Object> categories = (List<Object>) result;
      switch (categories.size()) {
        case 0:
          return null;
        case 1:
          Object expanded = removeIdentifierFields(categories.get(0));
          System.out.println("Result expanded:" + expanded);
          result = expanded;
          break;
        default:
          throw new IllegalArgumentException("Cannot convert a multi-category response"
              + " to a single category result");
      }
    }

    return mapper.convertValue(result, request.getResponseClass());
  }

  /**
   * Custom categories have a special format:
   */
  private Object removeIdentifierFields(Object json) {
    Map<String, Object> categoryJson = (Map<String, Object>) json;

    new HashMap<>(categoryJson).forEach((key, value) -> {
      if (value instanceof Map) {
        Map<String, Object> valueJson = (Map<String, Object>) value;
        String identifier = (String) valueJson.remove("identifier");

        if (identifier == null) {
          return;
        } else if (valueJson.isEmpty()) {
          categoryJson.remove(key);
        } else {
          categoryJson.put(identifier, valueJson);
        }
      }
    });

    return categoryJson;
  }
}
