package com.cargarantie.idoit.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class IdoitObject {

  protected int id;

  public abstract CategoryGeneral getGeneral();

  @JsonIgnore
  public String getTitle() {
    return getGeneral().getTitle();
  }
}
