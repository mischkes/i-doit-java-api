package com.cargarantie.idoit.api.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AllModels {
  Instance;

  static {
    register(Arrays.asList(StandardCategories.values()));
  }

  private Map<String, Class<?>> nameToClass = new HashMap<>();
  private Map<Class<?>, String> classToName = new HashMap<>();


  public static void register(List<IdoitModelReference> models) {
    models.forEach(m -> register(m.modelName(), m.modelClass()));
  }

  public static void register(String modelName, Class<?> modelClass) {
    Instance.nameToClass.put(modelName, modelClass);
    Instance.classToName.put(modelClass, modelName);
  }

  public static String getName(Object o) {
    return getName(o.getClass());
  }
  public static String getName(Class<?> clazz) {
    return Instance.classToName.get(clazz);
  }
  public static Class<?> getClass(String name) {
    return Instance.nameToClass.get(name);
  }
}
