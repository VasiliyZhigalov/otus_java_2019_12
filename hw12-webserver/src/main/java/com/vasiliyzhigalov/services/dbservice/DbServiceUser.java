package com.vasiliyzhigalov.services.dbservice;


import com.vasiliyzhigalov.model.User;

import java.util.List;
import java.util.Optional;

public interface DbServiceUser {

    long saveUser(User user);
    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);
    List<User> findAll();


}
