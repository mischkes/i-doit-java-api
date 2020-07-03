package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Value;

@Value
public class CategoryRead<T extends IdoitCategory> implements IdoitRequest<T> {

  private static final String METHOD = "cmdb.category.read";

  @JsonProperty("objID")
  ObjectId objId;
  Class<T> category;

  public String getCategory() {
    return Util.getCategoryName(category);
  }

  @Override
  public Class<T> getResponseClass() {
    return category;
  }

  @Override
  public T mapResponse(Object data) {
    return IdoitRequest.super.mapResponse(cleanResponse((List<Object>) data));
  }

  @Override
  public String getMethod() {
    return METHOD;
  }

  private Object cleanResponse(List<Object> categories) {
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
}
