package com.cargarantie.idoit.api.jsonrpc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdoitVersionResponse {

  private Login login;
  private String version;
  private String step;
  private String type;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Login {

    private int userid;
    private String name;
    private String mail;
    private String username;
    private String tenant;
    private String language;
  }
}
