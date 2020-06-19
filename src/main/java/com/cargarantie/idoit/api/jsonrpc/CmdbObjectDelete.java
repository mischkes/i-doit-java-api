package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CmdbObjectDelete extends IdoitRequest<SimpleSuccessResponse>{
  public enum DeleteAction {
    ARCHIVE("ARCHIVED"),
    DELETE("DELETED"),
    PURGE("PURGE");

    private final String deletionStatus;

    DeleteAction(String statusSuffix) {
      this.deletionStatus = "C__RECORD_STATUS__" + statusSuffix;
    }
  }

  private final int id;
  private final DeleteAction status;

  @Override
  public Class<SimpleSuccessResponse> getResponseClass() {
    return SimpleSuccessResponse.class;
  }

  public String getStatus() {
    return status.deletionStatus;
  }
}
