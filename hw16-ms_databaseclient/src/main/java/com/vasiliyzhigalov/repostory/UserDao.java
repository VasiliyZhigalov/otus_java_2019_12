package com.vasiliyzhigalov.repostory;



import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.repostory.sessionmanager.SessionManager;

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