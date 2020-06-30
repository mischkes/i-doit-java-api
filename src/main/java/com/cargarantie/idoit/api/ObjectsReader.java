package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.PrivilegedAccess.idoitObjectAccess;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

class ObjectsReader {

  private final IdoitSession session;

  public ObjectsReader(IdoitSession session) {
    this.session = session;
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    ObjectsRead<T> request = ObjectsRead.<T>builder().filterType(objectClass).build();
    return read(request);
  }

  public <T extends IdoitObject> Collection<T> read(ObjectsRead<T> delegateRequest) {
    if (delegateRequest.getFilterType() == null) {
      throw new IllegalArgumentException("Request needs to specify filterType");
    }

    Map<ObjectId, T> objectsById = readObjects(delegateRequest);
    Map<String, IdoitCategory> categories = readCategories(objectsById.values());
    return addCategoriesToObjects(objectsById, categories);
  }

  private <T extends IdoitObject> Map<ObjectId, T> readObjects(ObjectsRead<T> request) {
    ObjectsReadResponse response = session.send(request);

    Map<ObjectId, T> objectsById = new HashMap<>();
    response.getObjects().forEach(o -> {
      T newObject = Util.newInstance(request.getFilterType());
      idoitObjectAccess.setId(newObject, o.getId());
      objectsById.put(newObject.getId(), newObject);
    });

    return objectsById;
  }

  private <T extends IdoitObject> Map<String, IdoitCategory> readCategories(Collection<T> objects) {
    Batch<IdoitCategory> requests = new Batch<>();

    objects.forEach(object ->
        idoitObjectAccess.getCategoryClasses(object)
            .map(category -> new CategoryRead(object.getId(), category))
            .forEach(read -> requests.addWithPrefix("category", read))
    );

    return session.send(requests);
  }

  private <T extends IdoitObject> Collection<T> addCategoriesToObjects(Map<ObjectId, T> objects,
      Map<String, IdoitCategory> categories) {

    categories.values().stream().filter(Objects::nonNull).forEach(category -> {
      T object = Optional.ofNullable(objects.get(category.getObjId())).orElseThrow(
          () -> new NoSuchElementException("Category " + category
              + " has no object (id=" + category.getObjId()));

      idoitObjectAccess.setCategory(object, category);
    });

    return objects.values();
  }
}
