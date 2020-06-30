package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public class PrivilegedAccess {

  static IdoitObjectAccess idoitObjectAccess = (IdoitObjectAccess) Util
      .getStaticField(IdoitObject.class, "privilegedAccess");

  public interface IdoitObjectAccess {

    void setId(IdoitObject object, ObjectId id);

    Stream<IdoitCategory> getCategories(IdoitObject object);

    //only called from category that already has an id
    void setCategory(IdoitObject object, IdoitCategory category);

    Stream<Field> getCategoryFields(IdoitObject o);

    @JsonIgnore
    Stream<Class<?>> getCategoryClasses(IdoitObject object);
  }
}
