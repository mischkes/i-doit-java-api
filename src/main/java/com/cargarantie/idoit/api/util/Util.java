package com.cargarantie.idoit.api.util;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Scanner;
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

}
