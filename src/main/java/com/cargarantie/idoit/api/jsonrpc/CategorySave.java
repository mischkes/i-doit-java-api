package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CategorySave implements IdoitRequest<CategorySaveResponse> {

  private static final String METHOD = "cmdb.category.save";
  IdoitCategory data;
  Integer entry; //for multi-value categories, not supplying this will always create a new entry

  public CategorySave(IdoitCategory data) {
    this.data = data;
    this.entry = null;
  }

  public String getCategory() {
    return Util.getCategoryName(data);
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
