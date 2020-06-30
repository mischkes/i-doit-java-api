package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.config.TestConfig;
import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.CategorySaveResponse;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.jsonrpc.SimpleSuccessResponse;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.CategoryGeneralSimple;
import com.cargarantie.idoit.api.model.CategoryModel;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.annotation.CategoryName;
import com.cargarantie.idoit.api.model.annotation.ObjectTypeName;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class IdoitJavaApiIT {

  private static final String TITLE = "IdoitJavaApiIT test client";
  private static final String SYSID = TITLE + " SYSID";
  private static IdoitSession session;
  private static ObjectId objectId;

  @BeforeAll
  static void login() {
    IdoitClient client = new IdoitClient(TestConfig.demoIdoitCom());

    session = client.login();
  }

  @AfterAll
  static void logout() {
    session.close();
  }

  @Test
  @Order(100)
  void testCreateObject() {
    ObjectCreate request = new ObjectCreate("C__OBJTYPE__CLIENT", TITLE);

    ObjectCreateResponse response = session.getRpcClient().send(request);

    objectId = response.getId();
    assertThat(objectId.toInt()).isPositive();
  }

  @Test
  @Order(300)
  void testReadStandardCategory() {
    CategoryRead<CategoryGeneral> request = new CategoryRead<>(objectId,
        CategoryGeneral.class);

    CategoryGeneral actual = session.getRpcClient().send(request);

    CategoryGeneral expected = CategoryGeneral.builder()
        .objId(objectId).title(TITLE)
        .status(new Dialog(2, "Normal", "LC__CMDB__RECORD_STATUS__NORMAL", ""))
        .cmdbStatus(new Dialog(6, "in operation", "LC__CMDB_STATUS__IN_OPERATION",
            "C__CMDB_STATUS__IN_OPERATION"))
        .type(new Dialog(10, "Client", "LC__CMDB__OBJTYPE__CLIENT", "C__OBJTYPE__CLIENT"))
        .description("")
        .build();
    assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "objId", "title",
        "status", "purpose", "cmdbStatus", "type", "description");
  }

  @Test
  @Order(400)
  void testReadStandard_SimpleNotation() {
    CategoryRead<CategoryGeneralSimple> request = new CategoryRead<>(objectId,
        CategoryGeneralSimple.class);

    CategoryGeneralSimple actual = session.getRpcClient().send(request);

    CategoryGeneralSimple expected = CategoryGeneralSimple.builder()
        .objId(objectId).title(TITLE).status("Normal").cmdbStatus("in operation")
        .type("Client").description("").build();
    assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "objId", "title",
        "status", "purpose", "cmdbStatus", "type", "description");
  }

  @Test
  @Order(500)
  void testReadCustomCategory() {
    CategoryRead<MyGeneralCategory> request = new CategoryRead<>(objectId,
        MyGeneralCategory.class);

    MyGeneralCategory actual = session.getRpcClient().send(request);

    MyGeneralCategory expected = MyGeneralCategory.builder().title(TITLE).objId(objectId)
        .cmdbStatus(new Dialog(6, "in operation", "LC__CMDB_STATUS__IN_OPERATION",
            "C__CMDB_STATUS__IN_OPERATION")).description("").build();
    assertThat(actual).isEqualToIgnoringGivenFields(expected, "id", "changed");
    assertThat(actual.changed).isAfter(LocalDateTime.now().toLocalDate().atStartOfDay());
  }

  @Test
  @Order(550)
  void testInsertCategory() {
    CategoryModel model = CategoryModel.builder().manufacturer("Stark Industries")
        .objId(objectId).build();
    CategorySave request = new CategorySave(model);

    CategorySaveResponse response = session.getRpcClient().send(request);

    CategoryModel actual = session.getRpcClient()
        .send(new CategoryRead<>(objectId, CategoryModel.class));
    CategoryModel expected = CategoryModel.builder().manufacturer("Stark Industries")
        .objId(objectId).id(response.getEntry()).productid("").serviceTag("").serial("")
        .firmware("").description("").build();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @Order(600)
  void testUpdateCategory() {
    CategoryModel model = CategoryModel.builder().manufacturer("Umbrella Corporation")
        .objId(objectId).build();
    CategorySave request = new CategorySave(model);

    CategorySaveResponse response = session.getRpcClient().send(request);

    CategoryModel actual = session.getRpcClient()
        .send(new CategoryRead<>(objectId, CategoryModel.class));
    CategoryModel expected = CategoryModel.builder().manufacturer("Umbrella Corporation")
        .objId(objectId).id(response.getEntry()).productid("").serviceTag("").serial("")
        .firmware("").description("").build();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @Order(600)
  void testBatch() {
    Batch<CategorySaveResponse> batch = new Batch<CategorySaveResponse>()
        .add(new CategorySave(CategoryGeneral.builder().description("Batch this!")
            .objId(objectId).build()))
        .add(new CategorySave(CategoryModel.builder().manufacturer("Wayne Enterprises")
            .objId(objectId).build()));

    session.getRpcClient().send(batch);

    CategoryModel actualModel = session.getRpcClient()
        .send(new CategoryRead<>(objectId, CategoryModel.class));
    assertThat(actualModel.getManufacturer()).isEqualTo("Wayne Enterprises");
    CategoryGeneral actualGeneral = session.getRpcClient().send(new CategoryRead<>(objectId,
        CategoryGeneral.class));
    assertThat(actualGeneral.getDescription()).isEqualTo("Batch this!");
  }

  @Test
  @Order(650)
  void testPurgeObject() {
    ObjectDelete request = new ObjectDelete(objectId, DeleteAction.PURGE);

    SimpleSuccessResponse actual = session.getRpcClient().send(request);

    assertThat(actual).isNotNull();
  }

  @Test
  @Order(700)
  void testInsertFullObject() {
    Client expected = new Client(CategoryGeneral.builder().sysid(SYSID).title(TITLE).build());

    session.upsert(Collections.emptyList(), Collections.singletonList(expected));

    Client actual = readObjectClient(TITLE);
    assertThat(actual).isEqualToComparingOnlyGivenFields(expected,
        "general.sysid", "general.title");
  }

  @Test
  @Order(800)
  void testUpdateFullObject() {
    Client update = new Client(CategoryGeneral.builder().sysid(SYSID)
        .description("Some Description").build());
    List<GeneralObjectData> current = getGeneralObject(TITLE);

    session.upsert(current, Collections.singletonList(update));

    Client actual = readObjectClient(TITLE);
    Client expected = new Client(CategoryGeneral.builder().sysid(SYSID).title(TITLE)
        .description("Some Description").build());
    assertThat(actual).isEqualToComparingOnlyGivenFields(expected,
        "general.sysid", "general.title", "general.description");
  }

  @Test
  @Order(900)
  void testDeleteWhatWasPreviouslyInserted() {
    List<GeneralObjectData> current = getGeneralObject(TITLE);

    session.archive(current);

    List<GeneralObjectData> remainingObjects = getGeneralObject(TITLE);
    assertThat(remainingObjects).isEmpty();
  }

  private Client readObjectClient(String title) {
    Collection<Client> objects = session.read(getObjectsReadRequest(title));
    return objects.iterator().next();
  }

  private ObjectsRead<Client> getObjectsReadRequest(String title) {
    return ObjectsRead.<Client>builder().filterType(Client.class).filterTitle(title).build();
  }

  private List<GeneralObjectData> getGeneralObject(String title) {
    ObjectsReadResponse response = session.getRpcClient().send(getObjectsReadRequest(title));
    return response.getObjects();
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @ObjectTypeName("C__OBJTYPE__CLIENT")
  static class Client extends IdoitObject {

    CategoryGeneral general;
  }

  @Value
  @AllArgsConstructor
  @SuperBuilder
  @CategoryName("C__CATG__GLOBAL")
  static class MyGeneralCategory extends IdoitCategory {

    String title;
    LocalDateTime changed;
    Dialog cmdbStatus;
  }
}
