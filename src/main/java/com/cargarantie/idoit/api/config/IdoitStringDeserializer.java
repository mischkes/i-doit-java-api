package com.cargarantie.idoit.api.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Optional;
import lombok.SneakyThrows;

class IdoitStringDeserializer extends JsonDeserializer<String> {

  @Override
  public String deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {
    if (parser.hasTokenId(JsonTokenId.ID_START_OBJECT)) {
      ObjectNode object = parser.readValueAsTree();
      return Optional.ofNullable(object.get("title")).map(JsonNode::asText).orElseThrow(
          () -> noTitleException(parser));
    } else {
      return parser.getText();
    }
  }

  @SneakyThrows
  private IllegalArgumentException noTitleException(JsonParser parser) {
    return new IllegalArgumentException("Node " + parser.getCurrentName()
        + " does not declare field title");
  }
}
