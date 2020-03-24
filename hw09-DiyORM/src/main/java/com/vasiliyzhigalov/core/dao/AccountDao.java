package com.vasiliyzhigalov.core.dao;

import com.vasiliyzhigalov.core.model.Account;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    void update(Account objectData);

    Optional<Account> load(long id);

    void create(Account objectData);

    SessionManager getSessionManager();
}
