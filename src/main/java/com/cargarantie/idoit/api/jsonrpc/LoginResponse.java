package com.cargarantie.idoit.api.jsonrpc;

import lombok.Data;
import lombok.Value;

@Data
public class LoginResponse {
  String userid;
  String name;
  String mail;
  String username;
  String sessionId;
  String clientId;	//Tenant identifier
  String clientName; //Tenant name
}
