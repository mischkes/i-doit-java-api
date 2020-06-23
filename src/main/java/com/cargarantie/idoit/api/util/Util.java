package com.cargarantie.idoit.api.util;

import com.cargarantie.idoit.api.model.CategoryName;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.ObjectTypeName;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Optional;
import lombok.SneakyThrows;

public class Util {

  @SneakyThrows
  public static Object getField(Object containingObject, Field field) {
    field.setAccessible(true);
    return field.get(containingObject);
  }

  @SneakyThrows
  public static void setField(Object object, Field field, Object value) {
    field.setAccessible(true);
    field.set(object, value);
  }

  @SneakyThrows
  public static <T> T newInstance(Class<T> objectClass) {
    Constructor<T> constructor = objectClass.getDeclaredConstructor();
    constructor.setAccessible(true);
    return constructor.newInstance();
  }

  static <T extends Annotation> Optional<T> findAnnotation(Class<?> clazz,
      Class<T> annotationType) {
    return Optional.ofNullable(clazz.getDeclaredAnnotation(annotationType));
  }

  public static String getCategoryName(Class<? extends IdoitCategory> category) {
    return findAnnotation(category, CategoryName.class).map(CategoryName::value)
        .orElseThrow(() -> new IllegalArgumentException()); //TODO: complete and test
  }

  public static String getCategoryName(IdoitCategory category) {
    return getCategoryName(category.getClass());
  }

  public static String getObjectTypeName(Class<? extends IdoitObject> object) {
    return findAnnotation(object, ObjectTypeName.class).map(ObjectTypeName::value)
        .orElseThrow(() -> new IllegalArgumentException()); //TODO: complete and test
  }

  public static String getObjectTypeName(IdoitObject object) {
    return getObjectTypeName(object.getClass());
  }
}
