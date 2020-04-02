package com.vasiliyzhigalov.core.service;

import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;
import com.vasiliyzhigalov.jdbc.dao.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceEntityImpl<T> implements DBServiceEntity<T> {
    private static Logger logger = LoggerFactory.getLogger(DbServiceEntityImpl.class);

    private final DAO dao;

    public DbServiceEntityImpl(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(T objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.create(objectData);
                sessionManager.commitSession();
                logger.info("created objectData: {}", objectData);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void update(T objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.update(objectData);
                sessionManager.commitSession();
                logger.info("update objectData: {}", objectData);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> tOptional = (Optional<T>) dao.load(id, clazz);
                logger.info("user: {}", tOptional);
                return tOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
