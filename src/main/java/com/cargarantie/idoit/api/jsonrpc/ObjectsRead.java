package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Value
public class ObjectsRead<T extends IdoitObject> implements IdoitRequest<ObjectsReadResponse> {

  private static final String METHOD = "cmdb.objects.read";
  Filter filter;
  Ordering orderBy;

  @JsonIgnore
  Class<T> filterType;

  @Builder
  public ObjectsRead(Class<T> filterType, String filterTypeName, String filterTitle,
      String filterSysid, String filterEmail, String filterFirstName,
      String filterLastName,
      @Singular List<Integer> filterIds,
      Ordering orderBy) {

    if (filterType != null) {
      filterTypeName = Util.getObjectTypeName(filterType);
    }

    if (filterIds.size() == 0) {
      filterIds = null;
    }

    this.filter = new Filter(filterTypeName, filterTitle, filterSysid, filterEmail, filterFirstName,
        filterLastName, filterIds, null);
    this.orderBy = orderBy;
    this.filterType = filterType;
  }

  @Override
  public Class<ObjectsReadResponse> getResponseClass() {
    return ObjectsReadResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }

  public enum Ordering {
    EMAIL,
    FIRST_NAME,
    ID,
    LAST_NAME,
    SYSID,
    TITLE,
    TYPE,
    TYPE_TITLE;

    @JsonValue
    private String jsonValue() {
      return name().toLowerCase();
    }
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Filter {

    /**
     * Object type identifier (as integer), for example: 5. Alternatively, object type constant (as
     * string), for example: "C__OBJTYPE__SERVER"
     */
    private String type;
    private String title;
    private String sysid;
    private String email;
    private String firstName;
    private String lastName;
    private List<Integer> ids;


    /**
     * Translated name of object type, for example: "Server". Note: Set a proper language in your
     * request.
     */
    private String typeTitle;
  }
}
