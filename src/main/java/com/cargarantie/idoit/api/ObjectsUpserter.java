/*package com.cargarantie.idoit.api;

import com.cargarantie.idoit.syncjobs.idoit.model.IdoitCategory;
import com.cargarantie.idoit.syncjobs.idoit.model.IdoitCategoryName;
import com.cargarantie.idoit.syncjobs.idoit.model.IdoitObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idoit.api.cmdb.CMDBObject;
import com.idoit.api.cmdb.ObjectTypeConstants;
import com.idoit.api.cmdb.category.CMDBCategory;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@Slf4j
public class ObjectsUpserter {

  private final ObjectMapper mapper;

  @Autowired
  public ObjectsUpserter(@Qualifier("idoitMapper") ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  public <T extends IdoitObject> void upsert(IdoitSession session, Collection<T> currentObjects,
      Collection<T> updateObjects) {
    Map<Object, T> currentObjectsMapped = currentObjects.stream()
        .collect(Collectors.toMap(o -> o.getGeneral().getSysid(), Function.identity()));

    updateObjects
        .forEach(o -> upsert(session, currentObjectsMapped.get(o.getGeneral().getSysid()), o));
  }

  @SneakyThrows
  private <T extends IdoitObject> void upsert(IdoitSession session, T currentObject,
      T updateObject) {

    if (currentObject == null) {
      currentObject = createObject(session, updateObject);
    }

    Map<String, Object> change = new UpsertChangeBuilder(
        mapper.convertValue(currentObject, Map.class),
        mapper.convertValue(updateObject, Map.class)
    ).build();

    T changeObject = (T) mapper.convertValue(change, updateObject.getClass());
    if (changeObject != null) {
      log.info("Making change {}", changeObject);
      update(session, changeObject, currentObject);
    }
  }

  @SneakyThrows
  private <T extends IdoitObject> T createObject(IdoitSession session, T referenceObject) {
    CMDBObject newObject = session.object()
        .create(ObjectTypeConstants.TYPE_CLIENT, referenceObject.getTitle());

    T typedObject = (T) referenceObject.getClass().newInstance();
    typedObject.setId(newObject.getID());
    return typedObject;
  }

  private <T extends IdoitObject> void update(IdoitSession session, T dataObject, T idObject) {
    ReflectionUtils.doWithFields(dataObject.getClass(), field -> {
      if (field.getType().isAnnotationPresent(IdoitCategoryName.class)) {
        field.setAccessible(true);
        updateCategory(session, idObject.getId(), (IdoitCategory) field.get(dataObject),
            (IdoitCategory) field.get(idObject));
      }
    });
  }

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
  }
}
*/
