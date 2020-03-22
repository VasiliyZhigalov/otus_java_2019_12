package com.vasiliyzhigalov.jdbc.dao;

import com.vasiliyzhigalov.core.dao.DaoException;
import com.vasiliyzhigalov.core.dao.EntityDAO;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;
import com.vasiliyzhigalov.core.sqlmapper.SqlMapper;
import com.vasiliyzhigalov.jdbc.DbExecutor;
import com.vasiliyzhigalov.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAO implements EntityDAO<Object> {
    private static Logger logger = LoggerFactory.getLogger(DAO.class);
    private SqlMapper sqlMapper;
    private DbExecutor executor = new DbExecutor();
    private final SessionManagerJdbc sessionManager;

    public DAO(SessionManagerJdbc sessionManager, SqlMapper sqlMapper) {
        this.sessionManager = sessionManager;
        this.sqlMapper = sqlMapper;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public void create(Object object) {
        try {
            sqlMapper.setValues(object);
            String sql = sqlMapper.getSqlQueryInsert();
            logger.info(sql);
            executor.insertRecord(getConnection(), sql, sqlMapper.getValuesToString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Object object) {
        try {
            sqlMapper.setValues(object);
            List<String> values = new ArrayList<>(sqlMapper.getValuesToString());
            values.add(sqlMapper.getIdElementValue().toString());
            String sql = sqlMapper.getSqlQueryUpdate();
            logger.info(sql);
            executor.updateRecord(getConnection(), sql, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Object> load(long id, Class clazz) {
        String sql = sqlMapper.getSqlQuerySelect();
        logger.info(sql);
        try {
            return executor.selectRecord(getConnection(), sql, id, resultSet -> {
                try {
                    return sqlMapper.getObjectResult((ResultSet) resultSet);
                } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
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