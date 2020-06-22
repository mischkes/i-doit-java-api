package com.cargarantie.idoit.api.demo;

import com.cargarantie.idoit.api.IdoitSession;
import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.CategorySaveResponse;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.Version;
import com.cargarantie.idoit.api.jsonrpc.VersionResponse;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectBrowser;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Demo {

  private IdoitSession session;

  void directRpcRequest() {
    // Simple request with no dynamic parameters.
    // Response is typed, as the request specifies the response objects class
    VersionResponse version = session.send(new Version());
    System.out.println("Verion " + version.getVersion() + " Login: " + version.getLogin());

    // More complex request with several parameters
    // Create a category for an existing object: Define the category DTO, and combine
    // it with an object id inside a CmdbCategoryCreate
    CategoryContactAssignment category = CategoryContactAssignment.builder().id(CategoryId.of(123))
        .objId(ObjectId.of(234)).role("Admin").primary("yes").build();
    CategorySaveResponse created = session.send(new CategorySave(category));
    System.out.println(
        "Created category with id:" + created.getEntry() + " Message:" + created.getMessage());
  }

  void batchRpcRequest() {
    // A batch is constructed from multiple independent Rpc requests. Every request has a name,
    // which becomes the key for accessing the response
    Batch<CategorySaveResponse> batch = new Batch<>();
    batch.add("contact", new CategorySave(new CategoryContactAssignment()))
        .add("general", new CategorySave(new CategoryGeneral()))
        .add("contact2", new CategorySave(new CategoryContactAssignment()));

    Map<String, CategorySaveResponse> result = session.send(batch);

    System.out.println("Id of created general category is " + result.get("general").getEntry());
  }

  void fullObjectUpdates() {
    // An object consists of several categories, which consist of parameters. To update an object,
    // each of the changed categories needs 1 update request.

    // Read all CustomObjects that exist.
    List<GeneralObjectData> customObjects = null;//TODO: session.send(CmdbObjectsRead.builder().filter()CustomObject.class);
    customObjects.forEach(o -> System.out.println("Object with id " + o.getId() + ": " + o));

    // Get updated objects, maybe from an external source
    Collection<CustomObject> updateObjects = getUpdates();

    // Update all changed categories of all changed objects with a single statement. Updates will
    // be performed in batch.
    session.upsert(customObjects, updateObjects);
  }

  void categoryObjectHandling() {
    // Definition - most categories are defined like DTOs, with lombok magic as require.
    // Creation can use a builder. Complex parameters provide static factories. Parameters
    // only have 1 value for update, even when they have multiple values for read.
    CustomCategory category = CustomCategory.builder()
        .dateField(LocalDateTime.now())
        .dialogField(Dialog.fromConstant("C__DIALOG_CONSTANT__IN_OPERATION"))
        .objectReference(ObjectBrowser.of(4242))
        .simpleString("A simple String")
        .yesNoField(true)
        .description("Another String field")
        .build();

    // Access to category data. Some type conversions that are originally String, but nothing too
    // special
    LocalDateTime fromDateField = category.getDateField();
    String titleFromDialogField = category.getDialogField().getTitle();
    ObjectId idFromObjectReference = category.getObjectReference().getId();
    String simpleString = category.getSimpleString();
    Boolean yesNoField = category.getYesNoField();

  }

  private Collection<CustomObject> getUpdates() {
    //nothing to see here - just a placeholder
    return Collections.emptyList();
  }

}
