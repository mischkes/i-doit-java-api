package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

class IdoitObjectAccess {

  private static final Field ID_FIELD = Util.getField(IdoitObject.class, "id");

  public static void setId(IdoitObject object, ObjectId id) {
    Util.setField(object, ID_FIELD, id);
  }

  public static Stream<IdoitCategory> getCategories(IdoitObject object) {
    return getCategoryFields(object)
        .map(f -> Util.getField(object, f))
        .filter(IdoitCategory.class::isInstance)
        .map(IdoitCategory.class::cast);
  }


  public static Stream<Field> getCategoryFields(IdoitObject object) {
    return Arrays.stream(object.getClass().getDeclaredFields())
        .filter(f -> IdoitCategory.class.isAssignableFrom(f.getType()));
  }


  public static Stream<Class<?>> getCategoryClasses(IdoitObject object) {
    return getCategoryFields(object).map(Field::getType);
  }


  public static void setCategory(IdoitObject object, IdoitCategory category) {
    Field categoryField = getCategoryFields(object)
        .filter(field -> field.getType() == category.getClass())
        .findAny()
        .orElseThrow(() -> getUnassignableCategoryException(object, category));

    Util.setField(object, categoryField, category);
  }

  private static IllegalStateException getUnassignableCategoryException(
      IdoitObject object, IdoitCategory category) {
    return new IllegalStateException("Object " + object.getClass().getSimpleName()
        + " does not have category " + category.getClass().getSimpleName());
  }
}
