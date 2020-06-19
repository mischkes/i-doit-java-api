package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.model.IdoitObjectClient;
import com.cargarantie.idoit.api.model.TestModels;
import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ObjectsReaderIT {

  private final IdoitClient client = new IdoitClient(TestConfig.cgidoitdev());

  @BeforeAll
  static void beforeAll() {
    TestModels.register();
  }

  @Test
  void testReadOneClient() {
    try (IdoitSession session = client.login()) {
      CmdbObjectsRead<IdoitObjectClient> read = CmdbObjectsRead.<IdoitObjectClient>builder()
          .filterType(IdoitObjectClient.class).filterId(10898).build();

      Collection<IdoitObjectClient> objects = new ObjectsReader(session).read(read);
      System.out.println(objects);
    }
  }

  @Test
  void testReadClients() {
    try (IdoitSession session = client.login()) {
      Collection<IdoitObjectClient> read = new ObjectsReader(session).read(IdoitObjectClient.class);
      System.out.println(read);
    }
  }
}
