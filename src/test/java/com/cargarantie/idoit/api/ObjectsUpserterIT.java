package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryModel;
import com.cargarantie.idoit.api.model.IdoitObjectClient;
import com.cargarantie.idoit.api.model.param.Dialog;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(OrderAnnotation.class)
class ObjectsUpserterIT {

  public static final String INSERTED_OBJECT_TITLE = "i-doit-java-api test";
  public static final String INSERTED_OBJECT_SYSID = "i-doit-java-api test sysid";
  private final IdoitClient client = new IdoitClient(TestConfig.demoIdoitCom());

  @Test
  @Order(0)
  void testInsert() {
    testDeleteWhatWasPreviouslyInserted();
    IdoitObjectClient client = new IdoitObjectClient(
        CategoryGeneral.builder().sysid(INSERTED_OBJECT_SYSID).title(INSERTED_OBJECT_TITLE)
            .description("Created by " + this.getClass()).build(),
        CategoryModel.builder().manufacturer("Stark Industries").build());

    try (IdoitSession session = this.client.login()) {
      session.upsert(Collections.emptyList(), Arrays.asList(client));

      IdoitObjectClient expected = client;
      expected.getModel().setManufacturer("Stark Industries");
      expected.getGeneral().setStatus(
          Dialog.builder().id(2).title("Normal").titleLang("LC__CMDB__RECORD_STATUS__NORMAL")
              .constant("").build());
      expected.getGeneral().setCmdbStatus(
          Dialog.builder().id(6).title("in operation").titleLang("LC__CMDB_STATUS__IN_OPERATION")
              .constant("C__CMDB_STATUS__IN_OPERATION").build());
      IdoitObjectClient actual = getObjectClient(session, INSERTED_OBJECT_TITLE);
      assertThat(actual.getGeneral())
          .isEqualToIgnoringGivenFields(client.getGeneral(), "id", "created", "changed",
              "createdBy", "changedBy", "type");
    }
  }

  @Order(1)
  @Test
  void testUpdate() throws Exception {
    try (IdoitSession session = client.login()) {
      IdoitObjectClient update = getObjectClient(session, INSERTED_OBJECT_TITLE);
      update.getModel().setDescription("Welcome Model Description " + new Date().toString());
      update.getGeneral().setDescription("Welcome General " + new Date().toString());
      update.getGeneral().setStatus(
          null); //cannot update - String value of status won't be accepted. Maybe a constant or reference is needed?
      List<GeneralObjectData> current = getGeneralObject(session, INSERTED_OBJECT_TITLE);

      session.upsert(current, Arrays.asList(update));

      IdoitObjectClient reloadedClient = getObjectClient(session, INSERTED_OBJECT_TITLE);
      assertThat(reloadedClient.getModel()).isEqualTo(update.getModel());
      assertThat(reloadedClient.getGeneral())
          .isEqualToIgnoringGivenFields(update.getGeneral(), "status", "changed");
      assertThat(reloadedClient.getGeneral().getChanged())
          .isAfter(update.getGeneral().getChanged());
    }
  }

  @Order(3)
  @Test
  void testDeleteWhatWasPreviouslyInserted() {
    try (IdoitSession session = this.client.login()) {
      ObjectsReadResponse readResponse = session
          .send(ObjectsRead.builder().filterTitle(INSERTED_OBJECT_TITLE).build());
      List<GeneralObjectData> clientObjects = readResponse.getObjects();

      session.archive(clientObjects);

      ObjectsReadResponse remainingObjects = session
          .send(ObjectsRead.builder().filterTitle(INSERTED_OBJECT_TITLE).build());
      assertThat(remainingObjects.getObjects()).isEmpty();
    }
  }

  @Test
  void testUpsert() throws Exception {
    try (IdoitSession session = client.login()) {
      final String clientTitle = INSERTED_OBJECT_TITLE;
      IdoitObjectClient update = new IdoitObjectClient();
      update.setGeneral(CategoryGeneral.builder().title(INSERTED_OBJECT_TITLE)
          .sysid(INSERTED_OBJECT_SYSID)
          .description("Changed by upsert Test on " + new Date()).build());
      List<GeneralObjectData> current = getGeneralObject(session, clientTitle);

      session.upsert(current, Arrays.asList(update));

      IdoitObjectClient expected = getObjectClient(session, clientTitle);
      expected.getGeneral().setDescription(update.getGeneral().getDescription());
      IdoitObjectClient actual = getObjectClient(session, clientTitle);
      assertThat(actual.getModel()).isEqualTo(expected.getModel());
      assertThat(actual.getGeneral())
          .isEqualToIgnoringGivenFields(expected.getGeneral(), "changed");
      assertThat(actual.getGeneral().getChanged())
          .isAfterOrEqualTo(expected.getGeneral().getChanged());
    }

    testDeleteWhatWasPreviouslyInserted();
  }


  private IdoitObjectClient getObjectClient(IdoitSession session, String title) {
    Collection<IdoitObjectClient> objects = session.read(getObjectsReadRequest(title));
    return objects.iterator().next();
  }

  private List<GeneralObjectData> getGeneralObject(IdoitSession session, String title) {
    ObjectsReadResponse response = session.send(getObjectsReadRequest(title));
    return response.getObjects();
  }

  private ObjectsRead<IdoitObjectClient> getObjectsReadRequest(String title) {
    return ObjectsRead.<IdoitObjectClient>builder()
        .filterType(IdoitObjectClient.class).filterTitle(title).build();
  }
}
