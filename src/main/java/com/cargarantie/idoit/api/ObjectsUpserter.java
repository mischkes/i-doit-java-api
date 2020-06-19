package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategorySave;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class ObjectsUpserter {

  private final IdoitSession session;

  public ObjectsUpserter(IdoitSession session) {
    this.session = session;
  }

  @SneakyThrows
  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    Collection<GeneralObjectData> toDelete = mapUpdatedAndReturnUnused(
        currentObjects, updateObjects);

    createObjects(updateObjects.stream().filter(o -> o.getId() == 0).collect(Collectors.toList()));

    List<CmdbObjectDelete> archiveRequests = toDelete.stream().map(this::newArchiveRequest)
        .collect(Collectors.toList());

    List<CmdbCategorySave> updateRequests = updateObjects.stream().flatMap(object ->
        object.getCategories().peek(category -> category.setObjId(object.getId()))
            .map(CmdbCategorySave::new)).collect(Collectors.toList());

    session.send(new Batch<Object>("archive", archiveRequests)
        .addAll("update", updateRequests));
  }

  private <T extends IdoitObject> void createObjects(List<T> createObjects) {

    List<CmdbObjectCreate> createRequests = createObjects.stream()
        .map(CmdbObjectCreate::new).collect(Collectors.toList());

    Batch<ObjectCreateResponse> createBatch = new Batch<>();
    for (int i = 0; i < createObjects.size(); ++i) {
      createBatch.add(Integer.toString(i), createRequests.get(i));
    }
    Map<String, ObjectCreateResponse> createResponses = session.send(createBatch);
    for (int i = 0; i < createObjects.size(); ++i) {
      ObjectCreateResponse response = createResponses.get(Integer.toString(i));
      createObjects.get(i).setId(response.getId());
    }
  }

  private <T extends IdoitObject> Collection<GeneralObjectData> mapUpdatedAndReturnUnused(
      Collection<GeneralObjectData> currentObjects, Collection<T> updateObjects) {

    Map<String, GeneralObjectData> currentBySysid = currentObjects.stream()
        .collect(Collectors.toMap(o -> o.getSysid(), Function.identity()));

    updateObjects.forEach(up -> {
      GeneralObjectData current = currentBySysid.remove(up.getGeneral().getSysid());
      if (current != null) {
        up.setId(current.getId());
        up.getCategories().forEach(cat -> cat.setObjId(current.getId()));
      }
    });

    return currentBySysid.values();
  }

  private CmdbObjectDelete newArchiveRequest(GeneralObjectData generalObjectData) {
    return new CmdbObjectDelete(generalObjectData.getId(), DeleteAction.ARCHIVE);
  }
}
