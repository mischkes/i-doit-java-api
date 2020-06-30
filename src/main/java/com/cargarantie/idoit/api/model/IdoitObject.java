package com.cargarantie.idoit.api.model;

import com.cargarantie.idoit.api.PrivilegedAccess.IdoitObjectAccess;
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

  private static IdoitObjectAccess privilegedAccess = new IdoitObjectAccess() {
    @Override
    public void setId(IdoitObject object, ObjectId id) {
      object.id = id;
    }

    @Override
    public Stream<IdoitCategory> getCategories(IdoitObject object) {
      return getCategoryFields(object)
          .map(f -> Util.getField(object, f))
          .filter(IdoitCategory.class::isInstance)
          .map(IdoitCategory.class::cast);
    }

    @Override
    public Stream<Field> getCategoryFields(IdoitObject object) {
      return Arrays.stream(object.getClass().getDeclaredFields())
          .filter(f -> IdoitCategory.class.isAssignableFrom(f.getType()));
    }

    @Override
    public Stream<Class<?>> getCategoryClasses(IdoitObject object) {
      return getCategoryFields(object).map(Field::getType);
    }

    @Override
    public void setCategory(IdoitObject object, IdoitCategory category) {
      Field categoryField = getCategoryFields(object)
          .filter(field -> field.getType() == category.getClass())
          .findAny()
          .orElseThrow(() -> getUnassignableCategoryException(object, category));

      Util.setField(object, categoryField, category);
    }

    private IllegalStateException getUnassignableCategoryException(
        IdoitObject object, IdoitCategory category) {
      return new IllegalStateException("Object " + object.getClass().getSimpleName()
          + " does not have category " + category.getClass().getSimpleName());
    }
  };

  protected ObjectId id;

  public abstract TitleAndSysid getGeneral();

  @JsonIgnore
  public String getTitle() {
    return getGeneral().getTitle();
  }

  public GeneralObjectData toGeneralObject() {
    return GeneralObjectData.builder().id(id).sysid(getGeneral().getSysid()).build();
  }
}
