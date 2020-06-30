package com.cargarantie.idoit.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.jsonrpc.CategoryRead;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.CategoryId;
import com.cargarantie.idoit.api.model.param.Dialog;
import com.cargarantie.idoit.api.model.param.ObjectId;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

class JsonRpcResponseCleanerTest extends TestResourceAccess {

  @Test
  public void testClean() {
    CategoryRead<CustomCategory> request = new CategoryRead<>(ObjectId.of(0), CustomCategory.class);
    Object response = new ArrayList(Arrays.asList(getJson("customCategory", Object.class)));

    CustomCategory cleaned = new JsonRpcResponseCleaner().cleanResult(request, response);

    System.out.println(cleaned);
    CustomCategory expected = CustomCategory.builder().id(CategoryId.of(821))
        .objId(ObjectId.of(11457)).form(Dialog.fromTitle("a form")).os(null)
        .simpleString("a simple string").description("").build();
    assertThat(cleaned).isEqualTo(expected);
  }

  /*
{
  "id": "821",
  "objID": "11457",
  "form": {
    "identifier": "Form",
    "title": "a form"
  },
  "os": {
    "identifier": "OS"
  },
  "simple_test": "a simple string",
  "description": ""
}

   */

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @SuperBuilder
  @EqualsAndHashCode(callSuper = true)
  @ToString(callSuper = true)
  public static class CustomCategory extends IdoitCategory {

    private Dialog form;
    private Dialog os;
    private String simpleString;
  }
}
