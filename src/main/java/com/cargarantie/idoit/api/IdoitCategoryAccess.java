package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import java.lang.reflect.Field;

class IdoitCategoryAccess {

  private static final Field OBJ_ID_FIELD = Util.getField(IdoitCategory.class, "objId");

  static void setObjId(IdoitCategory category, ObjectId id) {
    Util.setField(category, OBJ_ID_FIELD, id);
  }
}
