package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
  public ObjectsRead(String filterFirstName, String filterLastName,
      @Singular List<Integer> filterIds,
      String filterTitle, Class<T> filterType, String filterTypeName, String filterEmail,
      String filterSysid, Ordering orderBy) {

    if (filterType != null) {
      filterTypeName = Util.getObjectTypeName(filterType);
    }

    if (filterIds.size() == 0) {
      filterIds = null;
    }

    this.filter = new Filter(filterFirstName, filterLastName, filterIds, filterTitle,
        filterTypeName,
        filterEmail, filterSysid, null);
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
    email,
    first_name,
    id,
    last_name,
    sysid,
    title,
    type,
    type_title
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Filter {

    private String firstName;
    private String lastName;
    private List<Integer> ids;
    private String title;

    /**
     * Object type identifier (as integer), for example: 5. Alternatively, object type constant (as
     * string), for example: "C__OBJTYPE__SERVER"
     */
    private String type;
    private String email;
    private String sysid;

    /**
     * Translated name of object type, for example: "Server". Note: Set a proper language in your
     * request.
     */
    private String typeTitle;
  }
}
