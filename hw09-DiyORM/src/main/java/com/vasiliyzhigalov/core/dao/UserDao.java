package com.vasiliyzhigalov.core.dao;


import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  void update(User objectData);

  Optional<User> load(long id);

  void create(User objectData);

  SessionManager getSessionManager();
}
