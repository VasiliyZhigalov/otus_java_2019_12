package com.vasiliyzhigalov.jdbc.dao;

import com.vasiliyzhigalov.cachehw.HwCache;
import com.vasiliyzhigalov.core.dao.DaoException;
import com.vasiliyzhigalov.core.dao.UserDao;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;
import com.vasiliyzhigalov.jdbc.DbExecutor;
import com.vasiliyzhigalov.jdbc.orm.Mapper;

import com.vasiliyzhigalov.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);
    private Mapper sqlMapper;
    private DbExecutor executor;
    private final SessionManagerJdbc sessionManager;
    private HwCache<Long, User> cache;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, Mapper sqlMapper, DbExecutor executor, HwCache<Long, User> cache) {
        this.sessionManager = sessionManager;
        this.sqlMapper = sqlMapper;
        this.executor = executor;
        this.cache = cache;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public void create(User user) {
        try {

            String sql = sqlMapper.getSqlQueryInsert();
            logger.info(sql);
            long userId = executor.executeInsert(getConnection(), sql, sqlMapper.getValues(user));
            if (cache != null) {
                cache.put(userId, user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            List<Object> values = new ArrayList<>(sqlMapper.getValues(user));
            long id = (long) sqlMapper.getIdElementValue(user);
            values.add(id);
            String sql = sqlMapper.getSqlQueryUpdate();
            logger.info(sql);
            if (cache != null) {
                cache.put(id, user);
            }
            executor.executeUpdate(getConnection(), sql, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> load(long id) {
        if (cache == null) {
            return loadFromDatabase(id);
        }
        User user = cache.get(id);
        if (user == null) {
            return loadFromDatabase(id);
        } else {
            return Optional.of(user);
        }
    }

    private Optional<User> loadFromDatabase(long id) {
        String sql = sqlMapper.getSqlQuerySelect();
        logger.info(sql);
        try {
            return executor.executeSelect(getConnection(), sql, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


}