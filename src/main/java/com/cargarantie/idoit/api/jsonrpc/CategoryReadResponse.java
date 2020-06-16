package com.cargarantie.idoit.api.jsonrpc;

import com.cargarantie.idoit.api.IdoitObjectMapper;
import com.cargarantie.idoit.api.model.IdoitCategory;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class CategoryReadResponse<T extends IdoitCategory> {
  private int id;
  private T result;
}
