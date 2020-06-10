package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Example:
{
    "version": "2.0",
    "method": "cmdb.category.create",
    "params": {
        "objID": 6676,
        "data": {
            "ipv4_address": "10.0.0.99",
            "hostname": "i.am.smol.de",
            "primary": 1,
            "active": 1

            },
        "catgID": "C__CATG__IP",
        "apikey": "???",
        "language": "en"
    },
    "id": 1
}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdbCategorySave extends IdoitRequest<CategorySaveResponse> {
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
}
