package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.param.ObjectId;
import lombok.Data;

@Data
public class ObjectDelete extends IdoitRequest<SimpleSuccessResponse> {

  public static final String METHOD = "cmdb.object.delete";
  private final ObjectId id;
  private final DeleteAction status;

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
