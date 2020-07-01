package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class IdoitObject {

  protected ObjectId id;

  public abstract TitleAndSysid getGeneral();

  @JsonIgnore
  public String getTitle() {
    return getGeneral().getTitle();
  }

  public GeneralObjectData toGeneralObject() {
    return GeneralObjectData.builder().id(id).sysid(getGeneral().getSysid()).build();
  }
}
