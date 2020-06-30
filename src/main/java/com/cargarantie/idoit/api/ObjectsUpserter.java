package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

class ObjectsUpserter {

  private final JsonRpcClient rpcClient;

  public ObjectsUpserter(JsonRpcClient rpcClient) {
    this.rpcClient = rpcClient;
  }

  @SneakyThrows
  public <T extends IdoitObject> void upsertObjects(Collection<T> currentObjects,
      Collection<T> updateObjects) {
    List<GeneralObjectData> currentGeneralObjects = currentObjects.stream()
        .map(IdoitObject::toGeneralObject)
        .collect(Collectors.toList());

    upsert(currentGeneralObjects, updateObjects);
  }

  @SneakyThrows
  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    Collection<GeneralObjectData> toDelete = mapUpdatedAndReturnUnused(
        currentObjects, updateObjects);

    createObjects(
        updateObjects.stream().filter(o -> o.getId() == null).collect(Collectors.toList()));

    List<ObjectDelete> archiveRequests = toDelete.stream().map(this::newArchiveRequest)
        .collect(Collectors.toList());

    List<CategorySave> updateRequests = updateObjects.stream().flatMap(object ->
        IdoitObjectAccess.getCategories(object)
            .peek(category -> IdoitCategoryAccess.setObjId(category, object.getId()))
            .map(CategorySave::new)).collect(Collectors.toList());

    rpcClient.send(new Batch<Object>("archive", archiveRequests)
        .addAll("update", updateRequests));
  }

  private <T extends IdoitObject> void createObjects(List<T> createObjects) {

    List<ObjectCreate> createRequests = createObjects.stream()
        .map(ObjectCreate::new).collect(Collectors.toList());

    Batch<ObjectCreateResponse> createBatch = new Batch<>();
    for (int i = 0; i < createObjects.size(); ++i) {
      createBatch.add(Integer.toString(i), createRequests.get(i));
    }
    Map<String, ObjectCreateResponse> createResponses = rpcClient.send(createBatch);
    for (int i = 0; i < createObjects.size(); ++i) {
      ObjectCreateResponse response = createResponses.get(Integer.toString(i));
      IdoitObjectAccess.setId(createObjects.get(i), response.getId());
    }
  }

  private <T extends IdoitObject> Collection<GeneralObjectData> mapUpdatedAndReturnUnused(
      Collection<GeneralObjectData> currentObjects, Collection<T> updateObjects) {

    Map<String, GeneralObjectData> currentBySysid = currentObjects.stream()
        .collect(Collectors.toMap(o -> o.getSysid(), Function.identity()));

    updateObjects.forEach(up -> {
      GeneralObjectData current = currentBySysid.remove(up.getGeneral().getSysid());
      if (current != null) {
        IdoitObjectAccess.setId(up, current.getId());
      }
    });

    return currentBySysid.values();
  }

  private ObjectDelete newArchiveRequest(GeneralObjectData generalObjectData) {
    return new ObjectDelete(generalObjectData.getId(), DeleteAction.ARCHIVE);
  }
}
