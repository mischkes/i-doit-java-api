package com.cargarantie.idoit.api.jsonrpc;

import static org.assertj.core.api.Assertions.assertThat;

import com.cargarantie.idoit.api.IdoitRequestsIT;
import com.cargarantie.idoit.api.model.CategoryContactAssignment;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.param.ObjectBrowser;
import com.cargarantie.idoit.api.model.param.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategorySaveIT extends IdoitRequestsIT {

  @Test
  void test_sendCategorySave_forUpdate() {
    mockRestResponse("CategorySaveResponse");
    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(ObjectBrowser.of(158)).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment, 871);

    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CategorySave_update", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(actualResponse.getEntry(),
        "Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_sendCategorySaveRequest_forCreate() {
    mockRestResponse("CategorySaveResponse");
    IdoitCategory contactAssignment = CategoryContactAssignment.builder().objId(ObjectId.of(1412))
        .contact(ObjectBrowser.of(158)).role("User").primary("no").build();
    CategorySave request = new CategorySave(contactAssignment);

    CategorySaveResponse actualResponse = client.send(request);

    Object expectedRequest = getJson("CategorySave_create", Object.class);
    assertThat(actualRequest).isEqualTo(expectedRequest);
    CategorySaveResponse expectedResponse = new CategorySaveResponse(actualResponse.getEntry(),
        "Category entry successfully saved");
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

}
