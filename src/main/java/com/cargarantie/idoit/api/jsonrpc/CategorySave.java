package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdbCategorySave extends IdoitRequest<CategorySaveResponse> {

  public static final String METHOD = "cmdb.category.save";
  private IdoitCategory data;
  private Integer entry; //for multi-value categories, not supplying this will always create a new entry

  public CmdbCategorySave(IdoitCategory data) {
    this.data = data;
  }

  public String getCategory() {
    return AllModels.getName(data);
  }

  public ObjectId getObject() {
    return data.getObjId();
  }

  @Override
  public Class<CategorySaveResponse> getResponseClass() {
    return CategorySaveResponse.class;
  }

  @Override
  public String getMethod() {
    return METHOD;
  }
}
