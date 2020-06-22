package com.cargarantie.idoit.api;

import static com.cargarantie.idoit.api.model.param.ObjectId.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CategorySave;
import com.cargarantie.idoit.api.jsonrpc.ObjectCreate;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete;
import com.cargarantie.idoit.api.jsonrpc.ObjectDelete.DeleteAction;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.model.AllModels;
import com.cargarantie.idoit.api.model.CategoryGeneral;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Arrays;
import java.util.Collections;
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

    upserter.upsert(Arrays.asList(current), Collections.emptyList());

    Batch<Object> expected = new Batch<>()
        .add("archive0", new ObjectDelete(of(42), DeleteAction.ARCHIVE));
    verify(session).send(expected);
  }

  @Test
  void testUpsert_shouldSendUpdate_forObjectInCurrentAndUpdateList() {
    ObjectsUpserter upserter = new ObjectsUpserter(session);
    GeneralObjectData current = GeneralObjectData.builder().id(of(42)).sysid("sys42").build();
    MyObject update = new MyObject("sys42", "title42");

    upserter.upsert(Arrays.asList(current), Arrays.asList(update));

    Batch<Object> expected = new Batch<>().add("update0", new CategorySave(
        CategoryGeneral.builder().objId(of(42)).sysid("sys42").title("title42").build()));
    verify(session).send(expected);
  }

  @Test
  void testUpsert_shouldSendCreateAndUpdate_forObjectOnlyInUpdateList() {
    ObjectsUpserter upserter = new ObjectsUpserter(session);
    MyObject update = new MyObject("sys42", "title42");

    upserter.upsert(Collections.emptyList(), Arrays.asList(update));

    verify(session).send(new Batch<>()
        .add("create0", new ObjectCreate("MY_OBJECT", "title42")));
    verify(session).send(new Batch<>()
        .add("update0", new CategorySave(update.general)));
  }

  @Data
  private static class MyObject extends IdoitObject {
    private CategoryGeneral general;

    public MyObject(String sysId, String title) {
      general = CategoryGeneral.builder().sysid(sysId).title(title).build();
    }
  }

  static {
    AllModels.register("MY_OBJECT", MyObject.class);
  }
}
