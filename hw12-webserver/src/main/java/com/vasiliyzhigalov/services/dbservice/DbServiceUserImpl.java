package com.vasiliyzhigalov.services.dbservice;

import com.vasiliyzhigalov.dao.UserDao;
import com.vasiliyzhigalov.model.User;
import com.vasiliyzhigalov.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DbServiceUser {

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
                sessionManager.commitSession();
                return userId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                return userOptional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findByLogin(login);
                return userOptional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<User> users = userDao.findAll();
                return users;
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return Collections.EMPTY_LIST;
        }
    }
}