package com.vasiliyzhigalov.dao;


import com.vasiliyzhigalov.model.User;
import com.vasiliyzhigalov.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    Optional<User> findRandomUser();

    Optional<User> findByLogin(String login);

    long saveUser(User user);

    SessionManager getSessionManager();

    List<User> findAll();
}