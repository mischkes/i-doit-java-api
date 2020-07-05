package com.cargarantie.idoit.api.jasonrpc;

import com.cargarantie.idoit.api.model.AllIdoitModels;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;

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
public class CmdbCategoryCreate extends IdoitRequest<CreateResponse> {
  private IdoitCategory data;

  public String getCatgId() {
    return AllIdoitModels.getName(data);
  }

  public ObjectId getObjID() {
    return data.getObjId();
  }

  @Override
  public Class<CreateResponse> getResponseClass() {
    return CreateResponse.class;
  }
}
