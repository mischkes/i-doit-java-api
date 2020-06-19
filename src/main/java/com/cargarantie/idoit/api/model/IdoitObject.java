package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.Data;

@Data
public abstract class IdoitObject {

  protected int id;

  public abstract CategoryGeneral getGeneral();

  @JsonIgnore
  public String getTitle() {
    return getGeneral().getTitle();
  }

  @JsonIgnore
  public Stream<IdoitCategory> getCategories() {
    return getCategoryFields()
        .map(f -> Util.getField(this, f))
        .map(IdoitCategory.class::cast);
  }

  @JsonIgnore
  public Stream<Class<?>> getCategoryClasses() {
    return getCategoryFields().map(Field::getType);
  }

  private Stream<Field> getCategoryFields() {
    return Arrays.stream(getClass().getDeclaredFields())
        .filter(f -> IdoitCategory.class.isAssignableFrom(f.getType()));
  }

  public void setCategory(IdoitCategory category) {
    Field categoryField = getCategoryFields().filter(field -> field.getType() == category.getClass())
        .findAny()
        .orElseThrow(() -> getUnassignableCategoryException(category));

    Util.setField(this, categoryField, category);
  }

  private IllegalStateException getUnassignableCategoryException(IdoitCategory category) {
    return new IllegalStateException("Object " + this.getClass().getSimpleName()
        + " does not have category " + category.getClass().getSimpleName());
  }
}
