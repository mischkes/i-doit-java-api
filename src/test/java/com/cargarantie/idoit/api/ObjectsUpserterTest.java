package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.model.param.ObjectId.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreateResponse;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitObject;
import com.cargarantie.idoit.api.model.ObjectTypeName;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectsUpserterTest {

  @Mock
  IdoitSession session;

  @Test
  void testUpsert_shouldSendArchiveRequest_forObjectOnlyInCurrentList() {
    ObjectsUpserter upserter = new ObjectsUpserter(session);
    GeneralObjectData current = GeneralObjectData.builder().id(of(42)).build();

    upserter.upsert(Collections.singletonList(current), Collections.emptyList());

    Batch<Object> expected = new Batch<>()
        .add("archive0", new ObjectDelete(of(42), DeleteAction.ARCHIVE));
    verify(session).send(expected);
  }

  @Test
  void testUpsert_shouldSendUpdate_forObjectInCurrentAndUpdateList() {
    ObjectsUpserter upserter = new ObjectsUpserter(session);
    GeneralObjectData current = GeneralObjectData.builder().id(of(42)).sysid("sys42").build();
    MyObject update = new MyObject("sys42", "title42");

    upserter.upsert(Collections.singletonList(current), Collections.singletonList(update));

    Batch<Object> expected = new Batch<>().add("update0", new CategorySave(
        CategoryGeneral.builder().objId(of(42)).sysid("sys42").title("title42").build()));
    verify(session).send(expected);
  }

  @Test
  void testUpsert_shouldSendCreateAndUpdate_forObjectOnlyInUpdateList() {
    ObjectsUpserter upserter = new ObjectsUpserter(session);
    when(session.send(new Batch<>()
        .add("0", new ObjectCreate("MY_OBJECT", "title42"))))
        .thenReturn(mapOf("0", new ObjectCreateResponse(ObjectId.of(999), "")));
    MyObject update = new MyObject("sys42", "title42");

    upserter.upsert(Collections.emptyList(), Collections.singletonList(update));

    verify(session).send(new Batch<>()
        .add("update0", new CategorySave(update.general)));
    assertThat(update.getId()).isEqualTo(ObjectId.of(999));
  }

  private Map<String, Object> mapOf(String key, Object value) {
    Map<String, Object> map = new HashMap<>();
    map.put(key, value);

    return map;
  }

  @Data
  @ObjectTypeName("MY_OBJECT")
  private static class MyObject extends IdoitObject {

    private CategoryGeneral general;

    public MyObject(String sysId, String title) {
      general = CategoryGeneral.builder().sysid(sysId).title(title).build();
    }
  }
}
