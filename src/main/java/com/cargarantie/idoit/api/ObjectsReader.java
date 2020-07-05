package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class ObjectsReader {

  private final JsonRpcClient rpcClient;

  public ObjectsReader(JsonRpcClient rpcClient) {
    this.rpcClient = rpcClient;
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    ObjectsRead<T> request = ObjectsRead.<T>builder().filterType(objectClass).build();
    return read(request);
  }

  public <T extends IdoitObject> Collection<T> read(ObjectsRead<T> objectsReadRequest) {
    if (objectsReadRequest.getFilterType() == null) {
      throw new IllegalArgumentException("Request needs to specify filterType");
    }

    Map<ObjectId, T> objectsById = readObjects(objectsReadRequest);
    Map<String, IdoitCategory> categories = readCategories(objectsById.values());
    return addCategoriesToObjects(objectsById, categories);
  }

  private <T extends IdoitObject> Map<ObjectId, T> readObjects(ObjectsRead<T> request) {
    ObjectsReadResponse response = rpcClient.send(request);

    return response.getObjects().stream().map(o -> {
      T newObject = Util.newInstance(request.getFilterType());
      IdoitObjectAccess.setId(newObject, o.getId());
      return newObject;
    }).collect(Collectors.toMap(t -> t.getId(), Function.identity()));
  }

  private <T extends IdoitObject> Map<String, IdoitCategory> readCategories(Collection<T> objects) {
    Batch<IdoitCategory> requests = new Batch<>();

    objects.forEach(object ->
        IdoitObjectAccess.getCategoryClasses(object)
            .map(category -> new CategoryRead(object.getId(), category))
            .forEach(read -> requests.addWithPrefix("category", read))
    );

    return rpcClient.send(requests);
  }

  private <T extends IdoitObject> Collection<T> addCategoriesToObjects(Map<ObjectId, T> objects,
      Map<String, IdoitCategory> categories) {

    categories.values().stream().filter(Objects::nonNull).forEach(category -> {
      Optional.ofNullable(objects.get(category.getObjId())).ifPresent(
          object -> IdoitObjectAccess.setCategory(object, category));
    });

    return objects.values();
  }
}
