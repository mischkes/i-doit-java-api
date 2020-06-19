package com.cargarantie.idoit.api.util;

import java.io.File;
import java.io.InputStream;
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
    return objectClass.newInstance();
  }

  public static String getResourceAsString(String name) {
    InputStream stream = Util.class.getClassLoader().getResourceAsStream(name);

    // use the Stupid Scanner trick from
    // https://community.oracle.com/blogs/pat/2004/10/23/stupid-scanner-tricks
    return new Scanner(stream, "UTF-8").useDelimiter("\\A").next();
  }
}
