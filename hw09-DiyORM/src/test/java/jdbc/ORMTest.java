package jdbc;

import com.vasiliyzhigalov.core.dao.AccountDao;
import com.vasiliyzhigalov.core.model.Account;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.service.DBServiceAccount;
import com.vasiliyzhigalov.core.service.DBServiceUser;
import com.vasiliyzhigalov.core.service.DbServiceAccountImpl;
import com.vasiliyzhigalov.core.service.DbServiceUserImpl;
import com.vasiliyzhigalov.jdbc.DbExecutor;
import com.vasiliyzhigalov.jdbc.dao.AccountDaoJdbc;
import com.vasiliyzhigalov.jdbc.orm.Mapper;
import com.vasiliyzhigalov.h2.DataSourceH2;
import com.vasiliyzhigalov.jdbc.dao.UserDaoJdbc;
import com.vasiliyzhigalov.jdbc.sessionmanager.SessionManagerJdbc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@DisplayName("ORM должен ")
public class ORMTest {
    private DataSource dataSource;
    private SessionManagerJdbc sessionManager;
    private DbExecutor dbExecutor;

    @BeforeEach
    public void setUp() {
        this.dataSource = new DataSourceH2();
        this.sessionManager = new SessionManagerJdbc(dataSource);
        this.dbExecutor = new DbExecutor();
    }

    @Test
    @DisplayName("создать таблицу User, добавить запись user, обновить user2, загрузить user3. user3 дожен быть равен user2")
    void TestUserORM() throws SQLException {
        Mapper sqlMapper = new Mapper(User.class);
        UserDaoJdbc dao = new UserDaoJdbc(sessionManager, sqlMapper, dbExecutor);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(dao);
        createUserTable(dataSource);
        User user = new User(0, "Inav", 23);
        dbServiceUser.create(user);
        User user2 = new User(1, "Jon", 44);
        dbServiceUser.update(user2);
        Optional<User> user3 = dbServiceUser.load(1);
        assertThat(user3).isNotEmpty().get().isEqualTo(user2);
        assertThat(user3).isNotEmpty().get().isNotEqualTo(user);
    }

    @Test
    @DisplayName("создать таблицу Account, добавить запись account, обновить account1, загрузить account2. account2 дожен быть равен account1")
    void TestAccountORM() throws SQLException {
        Mapper sqlMapper = new Mapper(Account.class);
        AccountDao dao = new AccountDaoJdbc(sessionManager, sqlMapper, dbExecutor);
        DBServiceAccount dbServiceUser = new DbServiceAccountImpl(dao);
        createAccountTable(dataSource);
        Account account = new Account(0, "private", 1);
        dbServiceUser.create(account);
        Account account1 = new Account(1, "company", 123);
        dbServiceUser.update(account1);
        Optional<Account> account2 = dbServiceUser.load(1);
        assertThat(account2).isNotEmpty().get().isEqualTo(account1);
        assertThat(account2).isNotEmpty().get().isNotEqualTo(account);
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("User table created");
    }


    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest int(5))")) {
            pst.executeUpdate();
        }
        System.out.println("Account table created");
    }
}
