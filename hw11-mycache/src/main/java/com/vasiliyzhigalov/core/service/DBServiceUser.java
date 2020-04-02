package com.vasiliyzhigalov.core.service;

import com.vasiliyzhigalov.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    void update(User objectData);

    Optional<User> load(long id);

    void create(User objectData);

}
