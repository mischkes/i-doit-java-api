package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.model.param.ObjectId;
import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class IdoitObject {

  protected ObjectId id;

  public abstract TitleAndSysid getGeneral();

  @JsonIgnore
  public String getTitle() {
    return getGeneral().getTitle();
  }

  @JsonIgnore
  public Stream<IdoitCategory> getCategories() {
    return getCategoryFields()
        .map(f -> Util.getField(this, f))
        .filter(IdoitCategory.class::isInstance)
        .map(IdoitCategory.class::cast);
  }

  @JsonIgnore
  public Stream<Class<?>> getCategoryClasses() {
    return getCategoryFields().map(Field::getType);
  }

  public void setCategory(IdoitCategory category) {
    Field categoryField = getCategoryFields()
        .filter(field -> field.getType() == category.getClass())
        .findAny()
        .orElseThrow(() -> getUnassignableCategoryException(category));

    Util.setField(this, categoryField, category);
  }

  public void setId(int id) {
    setId(ObjectId.of(id));
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public GeneralObjectData toGeneralObject() {
    return GeneralObjectData.builder().id(id).sysid(getGeneral().getSysid()).build();
  }

  private Stream<Field> getCategoryFields() {
    return Arrays.stream(getClass().getDeclaredFields())
        .filter(f -> IdoitCategory.class.isAssignableFrom(f.getType()));
  }

  private IllegalStateException getUnassignableCategoryException(IdoitCategory category) {
    return new IllegalStateException("Object " + this.getClass().getSimpleName()
        + " does not have category " + category.getClass().getSimpleName());
  }
}
