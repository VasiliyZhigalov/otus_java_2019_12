package com.vasiliyzhigalov.jdbc.dao;

import com.vasiliyzhigalov.core.dao.AccountDao;
import com.vasiliyzhigalov.core.dao.DaoException;
import com.vasiliyzhigalov.core.model.Account;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;
import com.vasiliyzhigalov.jdbc.DbExecutor;
import com.vasiliyzhigalov.jdbc.mapper.SqlMapper;
import com.vasiliyzhigalov.jdbc.sessionmanager.SessionManagerJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);
    private SqlMapper sqlMapper;
    private DbExecutor executor;
    private final SessionManagerJdbc sessionManager;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, SqlMapper sqlMapper, DbExecutor executor) {
        this.sessionManager = sessionManager;
        this.sqlMapper = sqlMapper;
        this.executor = executor;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public void create(Account account) {
        try {
            String sql = sqlMapper.getSqlQueryInsert();
            logger.info(sql);
            var values = sqlMapper.getValues(account);
            executor.insertRecord(getConnection(), sql, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Account account) {
        try {
            var values = new ArrayList<>(sqlMapper.getValues(account));
            values.add(sqlMapper.getIdElementValue());
            String sql = sqlMapper.getSqlQueryUpdate();
            logger.info(sql);
            executor.updateRecord(getConnection(), sql, values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> load(long id) {
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