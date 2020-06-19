package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitCategoryClientDescription;
import com.cargarantie.idoit.api.model.IdoitCategoryClientDescriptionSimple;
import com.cargarantie.idoit.api.model.IdoitObjectClient;
import com.cargarantie.idoit.api.model.IdoitObjectClientSimple;
import com.cargarantie.idoit.api.model.TestModels;
import com.cargarantie.idoit.api.model.param.Dialog;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(OrderAnnotation.class)
class ObjectsUpserterIT {

  public static final String INSERTED_OBJECT_TITLE =
      "NB-IdoitInsertObjectsServiceIT" + System.currentTimeMillis();
  private final IdoitClient client = new IdoitClient(TestConfig.cgidoitdev());

  @BeforeAll
  static void beforeAll() {
    TestModels.register();
  }

 /* @Test
  @Order(0)
  void testInsert() {
    IdoitObjectClient client = new IdoitObjectClient(
        CategoryGeneral.builder()
            .sysid("SYSID-IdoitInsertObjectsServiceIT" + System.currentTimeMillis())
            .title(INSERTED_OBJECT_TITLE).description("Created by " + this.getClass()).build(),
        IdoitCategoryClientDescription.builder().cpu(Dialog.fromTitle("FAAST")).build());

    try (IdoitSession session = this.client.login()) {
      session.upsert(Collections.emptyList(), Arrays.asList(client));

      IdoitObjectClient expected = client;
      expected.getDescription()
          .setCpu(Dialog.builder().id(51).title("FAAST").titleLang("FAAST").build());
      expected.getGeneral().setStatus(
          Dialog.builder().id(2).title("Normal").titleLang("LC__CMDB__RECORD_STATUS__NORMAL")
              .constant("").build());
      expected.getGeneral().setCmdbStatus(
          Dialog.builder().id(6).title("in operation").titleLang("LC__CMDB_STATUS__IN_OPERATION")
              .constant("C__CMDB_STATUS__IN_OPERATION").build());
      IdoitObjectClient actual = getClient(session, INSERTED_OBJECT_TITLE);
      assertThat(actual.getDescription()).isEqualTo(client.getDescription());
      assertThat(actual.getGeneral())
          .isEqualToIgnoringGivenFields(client.getGeneral(), "id", "created", "changed",
              "createdBy", "changedBy", "type");
    }
  }*/

  @Test
  void testInsertSimple() {
    IdoitObjectClientSimple client = new IdoitObjectClientSimple(
        CategoryGeneral.builder()
            .sysid("SYSID-IdoitInsertObjectsServiceIT" + System.currentTimeMillis())
            .title(INSERTED_OBJECT_TITLE).description("Created by " + this.getClass()).build(),
        IdoitCategoryClientDescriptionSimple.builder().cpu("FAAST").build());

    try (IdoitSession session = this.client.login()) {
      session.upsert(Collections.emptyList(), Arrays.asList(client));

      IdoitObjectClientSimple expected = client;
      expected.getGeneral().setStatus(
          Dialog.builder().id(2).title("Normal").titleLang("LC__CMDB__RECORD_STATUS__NORMAL")
              .constant("").build());
      expected.getGeneral().setCmdbStatus(
          Dialog.builder().id(6).title("in operation").titleLang("LC__CMDB_STATUS__IN_OPERATION")
              .constant("C__CMDB_STATUS__IN_OPERATION").build());
      IdoitObjectClientSimple actual = getClient(session, INSERTED_OBJECT_TITLE);
      assertThat(actual.getDescription()).isEqualTo(client.getDescription());
      assertThat(actual.getGeneral())
          .isEqualToIgnoringGivenFields(client.getGeneral(), "id", "created", "changed",
              "createdBy", "changedBy", "type");
    }
  }

  private IdoitObjectClientSimple getClient(IdoitSession session, String title) {
    CmdbObjectsRead<IdoitObjectClientSimple> readRequest = CmdbObjectsRead.<IdoitObjectClientSimple>builder()
        .filterType(IdoitObjectClientSimple.class).filterTitle(title).build();
    Collection<IdoitObjectClientSimple> objects = session.read(readRequest);

    return objects.iterator().next();
  }

  @Order(1)
  @Test
  void testDeleteWhatWasPreviouslyInserted() {
    try (IdoitSession session = this.client.login()) {
      ObjectsReadResponse readResponse = session
          .send(CmdbObjectsRead.builder().filterTitle(INSERTED_OBJECT_TITLE).build());
      GeneralObjectData clientObject = readResponse.getObjects().get(0);

      session.upsert(Collections.singletonList(clientObject), Collections.emptyList());

      ObjectsReadResponse remainingObjects = session
          .send(CmdbObjectsRead.builder().filterTitle(INSERTED_OBJECT_TITLE).build());
      assertThat(remainingObjects.getObjects()).isEmpty();
    }
  }
/*
  @Test
  void testUpdate() throws Exception {
    try (IdoitSession session = client.login()) {
      IdoitObjectClient update = getClient(session, "NB-IdoitUpdateObjectsServiceIT");
      update.getDescription().setDescription("Welcome Client Description " + new Date().toString());
      update.getGeneral().setDescription("Welcome General " + new Date().toString());
      update.getGeneral().setStatus(
          null); //cannot update - String value of status won't be accepted. Maybe a constant or reference is needed?
      IdoitObjectClient current = getClient(session, "NB-IdoitUpdateObjectsServiceIT");

      session.upsert(Arrays.asList(current), Arrays.asList(update));

      IdoitObjectClient reloadedClient = getClient(session, "NB-IdoitUpdateObjectsServiceIT");
      assertThat(reloadedClient.getDescription()).isEqualTo(update.getDescription());
      assertThat(reloadedClient.getGeneral())
          .isEqualToIgnoringGivenFields(update.getGeneral(), "status", "changed");
      assertThat(reloadedClient.getGeneral().getChanged())
          .isAfter(update.getGeneral().getChanged());
    }
  }

  @Test
  void testUpsert() throws Exception {
    try (IdoitSession session = client.login()) {
      final String clientTitle = "NB-IdoitUpsertObjectsServiceIT";
      IdoitObjectClient update = new IdoitObjectClient();
      update.setGeneral(CategoryGeneral.builder().sysid("SYSID_IdoitUpsertObjectsServiceTest")
          .description("Changed by upsert Test on " + new Date()).build());
      IdoitObjectClient current = getClient(session, clientTitle);

      session.upsert(session, Arrays.asList(current), Arrays.asList(update));

      IdoitObjectClient expected = getClient(session, clientTitle);
      expected.getGeneral().setDescription(update.getGeneral().getDescription());
      IdoitObjectClient actual = getClient(session, clientTitle);
      assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
      assertThat(actual.getGeneral())
          .isEqualToIgnoringGivenFields(expected.getGeneral(), "changed");
      assertThat(actual.getGeneral().getChanged())
          .isAfterOrEqualTo(expected.getGeneral().getChanged());
    }
  }*/
}
