package com.cargarantie.idoit.api;

public class TestConfig {
  public static ClientConfig cgidoitdev() {
    return new ClientConfig("http://cgidoitdev.cg.internal/", "=]BF-Q6e^yu443}]",
        "testSync", "kzw2SDQFeGWLrrR");
  }

  public static ClientConfig demoIdoitCom() {
    return new ClientConfig("https://demo.i-doit.com/", "c1ia5q",
        "admin", "admin");
  }

}
