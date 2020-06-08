package com.cargarantie.idoit.api.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AllIdoitModels {
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
    return Instance.classToName.get(o.getClass());
  }
}
