package com.vasiliyzhigalov.core.service;



import java.util.Optional;

public interface DBServiceEntity<T> {
  void create(T objectData);
  void update(T objectData);
  Optional<T> load(long id, Class<T> clazz);


}
