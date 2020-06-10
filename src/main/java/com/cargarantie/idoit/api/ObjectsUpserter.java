package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.SneakyThrows;

public class ObjectsUpserter {

  private final ObjectMapper mapper = IdoitObjectMapper.mapper;
  private final IdoitSession session;

  public ObjectsUpserter(IdoitSession session) {
    this.session = session;
  }

  @SneakyThrows
  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    Map<String, GeneralObjectData> currentObjectsMapped = currentObjects.stream()
        .collect(Collectors.toMap(o -> o.getSysid(), Function.identity()));

    LabeledObjects labeledObjects = new LabeledObjects();
    labeledObjects.toCreate = updateObjects.stream()
        .filter(o -> currentObjects.remove(o.getGeneral().getSysid()))
        .collect(Collectors.toList());
    labeledObjects.toDelete = currentObjectsMapped.values();
    labeledObjects.toUpsert = updateObjects;

    List<IdoitRequest<?>> changes;
  }

  @SneakyThrows
  private <T extends IdoitObject> void upsert(IdoitSession session, T currentObject,
      T updateObject) {

    if (currentObject == null) {
      currentObject = createObject(session, updateObject);
    }
  }

  @SneakyThrows
  private <T extends IdoitObject> T createObject(IdoitSession session, T referenceObject) {
 /*   CMDBObject newObject = session.object()
        .create(ObjectTypeConstants.TYPE_CLIENT, referenceObject.getTitle());

    T typedObject = (T) referenceObject.getClass().newInstance();
    typedObject.setId(newObject.getID());
    return typedObject;*/
    return null;
  }

  private <T extends IdoitObject> void update(IdoitSession session, T dataObject, T idObject) {
 /*   ReflectionUtils.doWithFields(dataObject.getClass(), field -> {
      if (field.getType().isAnnotationPresent(IdoitCategoryName.class)) {
        field.setAccessible(true);
        updateCategory(session, idObject.getId(), (IdoitCategory) field.get(dataObject),
            (IdoitCategory) field.get(idObject));
      }
    });*/
  }
/*
  @SneakyThrows
  private void updateCategory(IdoitSession session, int objectId, IdoitCategory category,
      IdoitCategory categoryId) {
    if (category == null) {
      return;
    }

    String categoryName = AnnotationUtils
        .findAnnotation(category.getClass(), IdoitCategoryName.class).value();
    Map<String, Object> values = mapper.convertValue(category, Map.class);
    values.remove("id");

    CMDBCategory updateDto = new CMDBCategory();
    values.entrySet().forEach(e -> updateDto.createValue(e.getKey()).setValue(e.getValue()));

    if (categoryId != null && categoryId.getId() > 0) {
      updateDto.createValue("id").setValue(categoryId.getId());
      session.category().update(objectId, categoryName, updateDto);
    } else {
      session.category().create(objectId, categoryName, updateDto);
      //it would be great to store the new category ID into the category object, so that it is
      //configured for future updates, but create() currently returns null. This means future
      //updates with the same category will crash
    }
  }*/

  @Data
  private static class LabeledObjects {

    Collection<GeneralObjectData> toDelete;
    Collection<? extends IdoitObject> toCreate;
    Collection<? extends IdoitObject> toUpsert;
  }
}
