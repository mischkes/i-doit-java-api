package com.cargarantie.idoit.api.util;

import java.net.URL;
import lombok.SneakyThrows;

public class Util {
  @SneakyThrows
  public static URL getResource(String fileName) {
    return Util.class.getClassLoader().getResource(fileName);
  }
}
