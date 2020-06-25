package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.model.param.CategoryId;
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
public class CategoryGeneralSimple extends IdoitCategory {

  private String title;
  private String status;
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
  private String purpose;
  private String category;
  private String sysid;
  private String cmdbStatus;
  private String type;

  @Builder
  public CategoryGeneralSimple(CategoryId id, ObjectId objId, String description, String title,
      String status, String purpose, String category, String sysid, String cmdbStatus,
      String type) {
    super(id, objId, description);
    this.title = title;
    this.status = status;
    this.purpose = purpose;
    this.category = category;
    this.sysid = sysid;
    this.cmdbStatus = cmdbStatus;
    this.type = type;
  }
}
