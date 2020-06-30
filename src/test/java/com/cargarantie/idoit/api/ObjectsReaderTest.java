package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.ObjectsReadResponse;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.TitleAndSysid;
import com.cargarantie.idoit.api.model.annotation.CategoryName;
import com.cargarantie.idoit.api.model.annotation.ObjectTypeName;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectsReaderTest {

  @Mock
  private IdoitSession session;

  @Test
  void readWithClassArg_shouldDelegateWithObjectsReadRequest() {
    ObjectsReader reader = spy(new ObjectsReader(session));
    doReturn(null).when(reader).read(any(ObjectsRead.class));

    reader.read(TestObject.class);

    verify(reader).read(ObjectsRead.<TestObject>builder().filterType(TestObject.class)
        .build());
  }

  @Test
  void read_shouldThrowException_whenObjectTypeMissing() {
    ObjectsReader reader = new ObjectsReader(session);

    assertThatThrownBy(() -> reader.read(ObjectsRead.<TestObject>builder().build()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Request needs to specify filterType");
  }

  @Test
  void read_shouldReadGeneralObjectDate_thenCategories_thenReturnIdoitObjects() {
    ObjectsReader reader = new ObjectsReader(session);
    ObjectsReadResponse objectsReadResponse = new ObjectsReadResponse(Arrays.asList(
        newGeneralObjectData(42), newGeneralObjectData(43), newGeneralObjectData(99)));
    when(session.send(any(ObjectsRead.class))).thenReturn(objectsReadResponse);
    when(session.send(any(Batch.class))).thenAnswer(this::categoryBatchResponse);

    Collection<TestObject> objects = reader.read(TestObject.class);

    verify(session).send(ObjectsRead.<TestObject>builder().filterType(TestObject.class).build());
    assertThat(objects).containsExactlyInAnyOrder(expectedTestObject(42), expectedTestObject(43),
        expectedTestObject(99));
  }

  private TestObject expectedTestObject(int i) {
    return new TestObject(
        TestCategory.builder().intField(i).objId(ObjectId.of(i)).build(),
        CategoryGeneral.builder().description("Description").objId(ObjectId.of(i)).build()
    );
  }

  private GeneralObjectData newGeneralObjectData(int id) {
    return GeneralObjectData.builder().id(ObjectId.of(id)).build();
  }

  private Object categoryBatchResponse(InvocationOnMock invocationOnMock) {
    Batch<IdoitCategory> argument = invocationOnMock.getArgument(0);
    Map<String, IdoitCategory> response = new HashMap<>();

    argument.getRequests().forEach((k, v) -> {
      CategoryRead<IdoitCategory> request = (CategoryRead<IdoitCategory>) v.getRequest();
      ObjectId objId = request.getObjId();

      if (CategoryGeneral.class.equals(request.getResponseClass())) {
        response.put(k, CategoryGeneral.builder().objId(objId).description("Description").build());
      } else {
        response.put(k, TestCategory.builder().objId(objId).intField(objId.toInt()).build());
      }
    });

    return response;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ObjectTypeName("myObj")
  static class TestObject extends IdoitObject {

    TestCategory category;
    CategoryGeneral general;

    @Override
    public TitleAndSysid getGeneral() {
      return general;
    }
  }

  @Data
  @SuperBuilder
  @CategoryName("myCat")
  private static class TestCategory extends IdoitCategory {

    int intField;
  }
}
