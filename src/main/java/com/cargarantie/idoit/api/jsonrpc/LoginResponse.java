package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

@Value
public class LoginResponse {
  String userid;
  String name;
  String mail;
  String username;

  @JsonProperty("session-id")
  String sessionId; //The seesion-Id that can used for login
  @JsonProperty("client-id")
  String clientId;	//Tenant identifier
  @JsonProperty("client-name")
  String clientName; //Tenant name
}
