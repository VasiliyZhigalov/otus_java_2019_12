package com.vasiliyzhigalov.core.dao;


import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    long saveUser(User user);

    Optional<User> findById(long id);

    SessionManager getSessionManager();

}
