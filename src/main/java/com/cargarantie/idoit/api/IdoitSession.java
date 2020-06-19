package com.cargarantie.idoit.api;

import com.cargarantie.idoit.api.jsonrpc.Batch;
import com.cargarantie.idoit.api.jsonrpc.CmdbCategoryRead;
import com.cargarantie.idoit.api.jsonrpc.CmdbObjectsRead;
import com.cargarantie.idoit.api.jsonrpc.GeneralObjectData;
import com.cargarantie.idoit.api.jsonrpc.IdoitRequest;
import com.cargarantie.idoit.api.model.IdoitCategory;
import com.cargarantie.idoit.api.model.IdoitObject;
import java.util.Collection;
import java.util.Map;

public class IdoitSession implements AutoCloseable {

  private final JsonRpcClient rpcClient;

  public IdoitSession(String url, String apiKey, String username, String password) {
    rpcClient = new JsonRpcClient(new RestClientWrapper(url + "/src/jsonrpc.php"), apiKey);
    rpcClient.login(username, password);
  }

  public <T extends IdoitObject> Collection<T> read(Class<T> objectClass) {
    return new ObjectsReader(this).read(objectClass);
  }

  public <T extends IdoitObject> Collection<T> read(CmdbObjectsRead<T> request) {
    return new ObjectsReader(this).read(request);
  }

  public <T extends IdoitObject> void upsert(Collection<GeneralObjectData> currentObjects,
      Collection<T> updateObjects) {
    new ObjectsUpserter(this).upsert(currentObjects, updateObjects);
  }

  public <T> Map<String, T> send(Batch<T> batch) {
    return rpcClient.send(batch);
  }

  public <T extends IdoitCategory> T send(CmdbCategoryRead<T> request) {
    return rpcClient.send(request);
  }

  public <T> T send(IdoitRequest<T> request) {
    return rpcClient.send(request);
  }

  @Override
  public void close() {
    //TODO
  }
}
