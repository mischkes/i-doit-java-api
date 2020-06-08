package com.cargarantie.idoit.api.demo;

import com.cargarantie.idoit.api.IdoitSession;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.rest.Batch;
import com.cargarantie.idoit.api.rest.CmdbCategoryCreate;
import com.cargarantie.idoit.api.rest.CreateResponse;
import com.cargarantie.idoit.api.rest.IdoitVersion;
import com.cargarantie.idoit.api.rest.IdoitVersionResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Demo {

  IdoitSession session;

  void directRpc() {
    //Typed responses, as the request specifies the response objects class
    IdoitVersionResponse version = session.send(new IdoitVersion());
    System.out.println("Verion " + version.getVersion() + " Login: " + version.getLogin());

    //Create a category for an existing object: Define the category DTO, and combine
    //it with an object id inside a CmdbCategoryCreate
    CreateResponse created = session.send(new CmdbCategoryCreate(123,
        new CategoryContactAssignment(234, "Admin", "yes")));
    System.out.println(
        "Created category with id:" + created.getId() + " Message:" + created.getMessage());

  }

  void batchRpc() {
    Batch<CreateResponse> batch = new Batch<>();
    batch.add("contact", new CmdbCategoryCreate(123, new CategoryContactAssignment()))
        .add("general", new CmdbCategoryCreate(123, new CategoryGeneral()))
        .add("contact2", new CmdbCategoryCreate(124, new CategoryContactAssignment()));

    Map<String, CreateResponse> result = session.send(batch);

    System.out.println("Id of created general category is " + result.get("general").getId());
  }

  void modifyingWholeObjects() {
    List<DemoObject> demoObjects = session.readObjects(DemoObject.class);
    demoObjects.forEach(o -> System.out.println("Object with id " + o.getId() + ": " + o));

    Collection<DemoObject> updateObjects = getUpdates();
    session.upsertObjects(demoObjects, updateObjects);
  }

  private Collection<DemoObject> getUpdates() {
    //nothing to see here - just a placeholder
    return Collections.emptyList();
  }

}
