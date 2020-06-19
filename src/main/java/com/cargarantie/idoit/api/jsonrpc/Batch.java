package com.cargarantie.idoit.api.jsonrpc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Batch<T> {

  private Map<String, NamedRequest<T>> requests = new HashMap<>();
  private final AtomicInteger idCounter = new AtomicInteger();

  public Batch(String prefix, Collection<? extends IdoitRequest<? extends T>> initialRequest) {
    addAll(prefix, initialRequest);
  }

  public Batch() {
  }

  public Batch<T> add(String name, IdoitRequest<? extends T> request) {
    requests.put(name, new NamedRequest<>(name, (IdoitRequest<T>) request));
    return this;
  }

  public Batch<T> addAll(String prefix, Collection<? extends IdoitRequest<? extends T>> requests) {
    for (IdoitRequest<? extends T> request : requests) {
      addWithPrefix(prefix, request);
    }

    return this;
  }

  public String getId(String prefix) {
    return prefix + idCounter.getAndIncrement();
  }

  public Batch<T> addWithPrefix(String prefix, IdoitRequest<? extends T> request) {
    return add(getId(prefix), request);
  }
  public Map<String, NamedRequest<T>> getRequests() {
    return requests;
  }
}
