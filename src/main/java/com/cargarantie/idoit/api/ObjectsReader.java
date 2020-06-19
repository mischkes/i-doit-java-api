package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectsReader {

  private final IdoitSession session;

  public ObjectsReader(IdoitSession session) {
    this.session = session;
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    CmdbObjectsRead<T> request = CmdbObjectsRead.<T>builder().filterType(objectClass).build();
    return read(request);
  }

  public <T extends IdoitObject> Collection<T> read(CmdbObjectsRead<T> request) {
    Map<ObjectId, T> objectsById = readObjects(request);
    Map<String, IdoitCategory> categories = readCategories(objectsById);
    return addCategoriesToObjects(objectsById, categories);
  }

  private <T extends IdoitObject> Map<ObjectId, T> readObjects(CmdbObjectsRead<T> request) {
    ObjectsReadResponse objectsReadResponse = session.send(request);
    Map<ObjectId, T> objectsById = new HashMap<>();

    objectsReadResponse.getObjects().forEach(o -> {
      T newObject = Util.newInstance(request.getFilterType());
      newObject.setId(o.getId());
      objectsById.put(ObjectId.of(o.getId()), newObject);

    });

    return objectsById;
  }

  private <T extends IdoitObject> Map<String, IdoitCategory> readCategories(
      Map<ObjectId, T> objectsById) {

    Batch<IdoitCategory> requests = new Batch<>();

    objectsById.forEach((id, object) ->
        object.getCategoryClasses().map(category -> new CmdbCategoryRead(id.toInt(), category))
            .forEach(read -> requests.addWithPrefix("category", read))
    );

    return session.send(requests);
  }

  private <T extends IdoitObject> Collection<T> addCategoriesToObjects(Map<ObjectId, T> objects,
      Map<String, IdoitCategory> categories) {

    categories.values().stream().filter(Objects::nonNull).forEach(category -> {
      T object = objects.get(category.getObjId()); //TODO: maybe null check?
      object.setCategory(category);
    });

    return objects.values();
  }
}
