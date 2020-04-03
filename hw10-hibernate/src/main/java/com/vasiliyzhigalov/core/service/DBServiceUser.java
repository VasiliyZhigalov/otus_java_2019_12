package com.vasiliyzhigalov.core.service;

import com.vasiliyzhigalov.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> findById(long id);

}
