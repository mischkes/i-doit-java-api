package com.cargarantie.idoit.api;


import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

public class ObjectsReader {
/*
  private final ObjectMapper mapper;

  @Autowired
  public ObjectsReader(ObjectMapper mapper) {
    this.mapper = mapper;
  }
*/
  @SneakyThrows
  public <T extends IdoitObject> List<T> getObjects(IdoitSession session, Class<T> objectClass) {
    String objectType = /*someReflection*/ null;

    return null; //loadObjects(session, objectClass, session.objects().filterType(objectType).read()); //new jsonrpc object
  }
/*
  public <T extends IdoitObject> List<T> loadObjects(IdoitSession session, Class<T> objectClass,
      List<CMDBObject> idoitObjects) {

    return idoitObjects.parallelStream() //building Batch. Assembly of Result to one Object
        .map(CMDBObject::getID)
        .map(id -> collectCategories(session, id, objectClass))
        .collect(Collectors.toList());
  }

  @SneakyThrows
  protected <T extends IdoitObject> T collectCategories(IdoitSession session, int id,
      Class<T> objectClass) {

    T target = objectClass.newInstance();
    target.setId(id);

    ReflectionUtils.doWithFields(objectClass, field -> {
      if (field.getType().isAnnotationPresent(IdoitCategoryName.class)) {
        Object categoryDto = getCategoryDtoForId(session, id, field.getType());
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, categoryDto);
      }
    });

    return target;
  }

  //TODO ITAUF-1113: deal with List<IdoitCategory annotated> typs
  @SneakyThrows
  private <T> T getCategoryDtoForId(IdoitSession session, int id, Class<T> targetClass) {

    IdoitCategoryName annotation = AnnotationUtils
        .findAnnotation(targetClass, IdoitCategoryName.class);
    Collection<CMDBCategory.Value> values =
        session.category().read(id, annotation.value())
            .stream()
            .map(CMDBCategory::values)
            .findFirst()
            .orElseGet(Collections::emptyList);

    if (values.isEmpty()) {
      log.info("no Data found for id: {} in {}", id, annotation.value());
    }

    return convertCategoryDto(values.stream()
            //avoid bug JDK-8148463 in Collectors.toMap, which cannot deal with null values
            .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll),
        targetClass);
  }

  private <T> T convertCategoryDto(HashMap<String, Object> values, Class<T> targetDto) {
    return mapper.convertValue(values, targetDto);
  }*/
}
