package com.vasiliyzhigalov.core.service;

import com.vasiliyzhigalov.core.model.Account;
import com.vasiliyzhigalov.core.model.User;

import java.util.Optional;

public interface DBServiceAccount {

    void update(Account objectData);

    Optional<Account> load(long id);

    void create(Account objectData);

}
