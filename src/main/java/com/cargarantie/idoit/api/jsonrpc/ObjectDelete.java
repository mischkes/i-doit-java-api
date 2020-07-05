package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value
public class ObjectDelete implements IdoitRequest<SimpleSuccessResponse> {

  private static final String METHOD = "cmdb.object.delete";
  ObjectId id;
  DeleteAction status;

  @Override
  public Class<SimpleSuccessResponse> getResponseClass() {
    return SimpleSuccessResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }

  public enum DeleteAction {
    ARCHIVE("ARCHIVED"),
    DELETE("DELETED"),
    PURGE("PURGE");

    @JsonValue
    private final String deletionStatus;

    DeleteAction(String statusSuffix) {
      this.deletionStatus = "C__RECORD_STATUS__" + statusSuffix;
    }
  }
}
