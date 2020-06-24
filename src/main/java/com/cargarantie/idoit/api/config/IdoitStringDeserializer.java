package com.cargarantie.idoit.api.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

class IdoitStringDeserializer extends JsonDeserializer<String> {

  @Override
  public String deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {
    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      ObjectNode object = parser.readValueAsTree();
      return object.get("title").asText(); //do the same for obbjects-references and IDs
      //maybe check sensible error messages
    } else {
      return parser.getText();
    }
  }
}
