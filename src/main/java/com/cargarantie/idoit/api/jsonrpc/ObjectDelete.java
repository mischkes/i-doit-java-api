package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
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

  public String getStatus() {
    return status.deletionStatus;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }

  public enum DeleteAction {
    ARCHIVE("ARCHIVED"),
    DELETE("DELETED"),
    PURGE("PURGE");

    private final String deletionStatus;

    DeleteAction(String statusSuffix) {
      this.deletionStatus = "C__RECORD_STATUS__" + statusSuffix;
    }
  }
}
