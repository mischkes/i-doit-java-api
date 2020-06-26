package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.MultiSelect;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@CategoryName("C__CATG__GLOBAL")
public class CategoryGeneral extends IdoitCategory implements TitleAndSysid {

  private String title;
  private Dialog status;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PACKAGE)
  private LocalDateTime created;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PACKAGE)
  private String createdBy;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PACKAGE)
  private LocalDateTime changed;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Setter(AccessLevel.PACKAGE)
  private String changedBy;
  private Dialog purpose;
  private Dialog category;
  private String sysid;
  private Dialog cmdbStatus;
  private Dialog type;
  private MultiSelect tag;

  @Builder
  public CategoryGeneral(CategoryId id, ObjectId objId, String description, String title,
      Dialog status, Dialog purpose, Dialog category, String sysid, Dialog cmdbStatus, Dialog type,
      MultiSelect tag) {
    super(id, objId, description);
    this.title = title;
    this.status = status;
    this.purpose = purpose;
    this.category = category;
    this.sysid = sysid;
    this.cmdbStatus = cmdbStatus;
    this.type = type;
    this.tag = tag;
  }
}
