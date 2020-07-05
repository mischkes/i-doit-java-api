package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.config.ClientConfig;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.ObjectsRead;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IdoitSession implements AutoCloseable {

  private final JsonRpcClient rpcClient;

  public IdoitSession(ClientConfig cfg) {
    rpcClient = new JsonRpcClient(new RestClientWrapper(cfg.getApiEndpoint()), cfg.getApiKey());
    rpcClient.login(cfg.getUsername(), cfg.getPassword());
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    return new ObjectsReader(rpcClient).read(objectClass);
  }

  public <T extends IdoitObject> Collection<T> read(ObjectsRead<T> delegateRequest) {
    return new ObjectsReader(rpcClient).read(delegateRequest);
  }

  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    new ObjectsUpserter(rpcClient).upsert(currentObjects, updateObjects);
  }

  public <T extends IdoitObject> void upsertObjects(Collection<T> currentObjects,
      Collection<T> updateObjects) {
    new ObjectsUpserter(rpcClient).upsertObjects(currentObjects, updateObjects);
  }

  public void archive(List<GeneralObjectData> clientObjects) {
    upsert(clientObjects, Collections.emptyList());
  }

  public JsonRpcClient getRpcClient() {
    return rpcClient;
  }

  @Override
  public void close() {
    rpcClient.logout();
  }
}
