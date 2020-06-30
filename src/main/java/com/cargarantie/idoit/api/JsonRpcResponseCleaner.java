package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.IdoitObjectMapper;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

class JsonRpcResponseCleaner {

  private final ObjectMapper mapper = IdoitObjectMapper.getObjectMapper();

  public <T> T cleanResult(IdoitRequest<T> request, Object result) {

    if (request instanceof CategoryRead) {
      // CmdbCategoryRead only parses non-multi list. Then, result is always a list of 1.
      result = mapToSingleCategory((List<Object>) result);
    }

    return mapper.convertValue(result, request.getResponseClass());
  }

  /**
   * Custom category fields have a special format:
   * <pre>
   *       "f_popup_c_1581595935188": {
   *         "identifier": "Form"
   *       }
   * </pre>
   * which will still be present even when there is no data other than the identifier. This would
   * causes the creation of empty parameter objects, instead of null values. We want null values, so
   * we clean identifier-only parameters
   */
  private Map<String, Object> removeIdentifierFields(Map<String, Object> json) {
    Set<Entry<String, Object>> entries = json.entrySet();

    return Stream.concat(
        entries.stream().filter(e -> !(e.getValue() instanceof Map)),
        entries.stream().filter(e -> e.getValue() instanceof Map)
            .peek(e -> ((Map<?, ?>) e.getValue()).remove("identifier"))
            .filter(e -> !((Map<?, ?>) e.getValue()).isEmpty())
    ).collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
  }

  private Object mapToSingleCategory(List<Object> categories) {
    switch (categories.size()) {
      case 0:
        return null;
      case 1:
        return removeIdentifierFields((Map<String, Object>) categories.get(0));
      default:
        throw new IllegalArgumentException("Cannot convert a multi-category response"
            + " to a single category result");
    }
  }
}
