package com.cargarantie.idoit.api.jsonrpc;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {
  ObjectMapper mapper = new ObjectMapper();

  <T> T send(IdoitRequest<T> request) {
    //send request
    //parse result
    /*new JsonRpcRequest<>()
    JsonRpcResult result = new JsonRpcResult();
    return mapper.convertValue(result.getResult(), request.getParams().getResponseClass());*/

    return null;
  }


 /* <T> Map<String, T> sendBatch(List<NamedRequestJsonRpcRequestWrapper<? extends IdoitRequest<T>> request) {
    //send request
    //parse result
    JsonRpcResult result = new JsonRpcResult();
    return mapper.convertValue(result.getResult(), request.getParams().getResponseClass());
  }*/
}
