package com.vasiliyzhigalov.services;


import com.vasiliyzhigalov.dao.UserDao;
import com.vasiliyzhigalov.sessionmanager.SessionManager;

import java.util.Optional;

public class UserAuthServiceImpl implements UserAuthService {

    private final UserDao userDao;

    public UserAuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        try (SessionManager sessionManager = userDao.getSessionManager();) {
            try {
                sessionManager.beginSession();
                return userDao.findByLogin(login)
                        .map(user -> user.getPassword().equals(password))
                        .orElse(false);
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
        }
        return false;
    }
}
