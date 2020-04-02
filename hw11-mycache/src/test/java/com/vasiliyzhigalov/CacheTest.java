package com.vasiliyzhigalov;

import com.vasiliyzhigalov.cachehw.HWCacheDemo;
import com.vasiliyzhigalov.cachehw.HwCache;
import com.vasiliyzhigalov.cachehw.HwListener;
import com.vasiliyzhigalov.cachehw.MyCache;
import com.vasiliyzhigalov.core.dao.UserDao;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.service.DBServiceUser;
import com.vasiliyzhigalov.core.service.DbServiceUserImpl;
import com.vasiliyzhigalov.h2.DataSourceH2;
import com.vasiliyzhigalov.jdbc.DbExecutor;
import com.vasiliyzhigalov.jdbc.dao.UserDaoJdbc;
import com.vasiliyzhigalov.jdbc.orm.Mapper;
import com.vasiliyzhigalov.jdbc.sessionmanager.SessionManagerJdbc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис должен ")
public class CacheTest {
    private DataSource dataSource;
    private SessionManagerJdbc sessionManager;
    private DbExecutor dbExecutor;
    private HwCache<Long, User> userCache;
    private HwListener<Long, User> userListener;
    private static Logger logger = null;
    private Mapper sqlMapper;
    private UserDao userDaoWithCache;
    private UserDao userDaoWithoutCache;
    private DBServiceUser dbServiceUser;
    private static final int NUMB_USERS = 50;

    @BeforeEach
    public void setUp() {
        this.logger = LoggerFactory.getLogger(HWCacheDemo.class);
        this.dataSource = new DataSourceH2();
        this.sessionManager = new SessionManagerJdbc(dataSource);
        this.dbExecutor = new DbExecutor();
        this.userCache = new MyCache<>();
        this.sqlMapper = new Mapper(User.class);
        this.userListener = new HwListener<Long, User>() {
            @Override
            public void notify(Long key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        this.userCache.addListener(this.userListener);
        this.userDaoWithoutCache = new UserDaoJdbc(sessionManager, sqlMapper, dbExecutor, null);
        this.userDaoWithCache = new UserDaoJdbc(sessionManager, sqlMapper, dbExecutor, userCache);
    }

    @Test
    @DisplayName("создать таблицу User, добавить  в нее 100 записей, сохраняя их в кэш, затем загузить из кэша и из бд сравнить время, из кэша должно быть быстее")
    public void cacheTest() throws SQLException {
        createUserTable(dataSource);
        dbServiceUser = new DbServiceUserImpl(userDaoWithCache);
        saveUsers();
        long start = System.currentTimeMillis();
        getUsers();
        long finish = System.currentTimeMillis();
        long timeWithCache = finish - start;
        dbServiceUser = new DbServiceUserImpl(userDaoWithoutCache);
        start = System.currentTimeMillis();
        getUsers();
        finish = System.currentTimeMillis();
        long timeWithoutCache = finish - start;
        System.out.println("Time with cache:" + timeWithCache);
        System.out.println("Time without cache:" + timeWithoutCache);
        assertThat(timeWithoutCache - timeWithCache).isPositive();
        this.userCache.removeListener(userListener);
    }

    private void saveUsers() {
        for (int i = 0; i < NUMB_USERS; i++) {
            dbServiceUser.create(new User(0, "Ivan" + 1, i + 1));
        }
    }

    private void getUsers() {
        for (int i = 0; i < NUMB_USERS; i++) {
            dbServiceUser.load(i + 1);
        }
    }


    private void createUserTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("User table created");
    }
}
