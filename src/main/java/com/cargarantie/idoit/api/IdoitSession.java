package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IdoitSession implements AutoCloseable {

  private final JsonRpcClient rpcClient;

  public IdoitSession(ClientConfig cfg) {
    rpcClient = new JsonRpcClient(new RestClientWrapper(cfg.getApiEndpoint()), cfg.getApiKey());
    rpcClient.login(cfg.getUsername(), cfg.getPassword());
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    return new ObjectsReader(this).read(objectClass);
  }

  public <T extends IdoitObject> Collection<T> read(ObjectsRead<T> delegateRequest) {
    return new ObjectsReader(this).read(delegateRequest);
  }

  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    new ObjectsUpserter(this).upsert(currentObjects, updateObjects);
  }

  public <T> Map<String, T> send(Batch<T> batch) {
    return rpcClient.send(batch);
  }

  public <T> T send(IdoitRequest<T> request) {
    return rpcClient.send(request);
  }

  @Override
  public void close() {
    rpcClient.logout();
  }

  public void archive(List<GeneralObjectData> clientObjects) {
    upsert(clientObjects, Collections.emptyList());
  }
}
