package jdbc;

import com.vasiliyzhigalov.core.model.Account;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.service.DbServiceEntityImpl;
import com.vasiliyzhigalov.core.sqlmapper.SqlMapper;
import com.vasiliyzhigalov.h2.DataSourceH2;
import com.vasiliyzhigalov.jdbc.dao.DAO;
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

    @BeforeEach
    public void setUp() {
        this.dataSource = new DataSourceH2();
        this.sessionManager = new SessionManagerJdbc(dataSource);

    }

    @Test
    @DisplayName("создать таблицу User, добавить запись user, обновить user2, загрузить user3. user3 дожен быть равен user2")
    void TestUserORM() throws SQLException {
        SqlMapper sqlMapper = new SqlMapper(User.class);
        DAO dao = new DAO(sessionManager, sqlMapper);
        DbServiceEntityImpl dbServiceUser = new DbServiceEntityImpl(dao);
        createUserTable(dataSource);
        User user = new User(0, "Inav", 22);
        dbServiceUser.create(user);
        User user2 = new User(1, "Jon", 44);
        dbServiceUser.update(user2);
        Optional<User> user3 = dbServiceUser.load(1, User.class);
        assertThat(user3.get()).isEqualTo(user2);
        assertThat(user3.get()).isNotEqualTo(user);
    }
    @Test
    @DisplayName("создать таблицу Account, добавить запись account, обновить account1, загрузить account2. account2 дожен быть равен account1")
    void TestAccountORM() throws SQLException {
        SqlMapper sqlMapper = new SqlMapper(Account.class);
        DAO dao = new DAO(sessionManager, sqlMapper);
        DbServiceEntityImpl dbServiceUser = new DbServiceEntityImpl(dao);
        createAccountTable(dataSource);
        Account account = new Account(0, "private", 1);
        dbServiceUser.create(account);
        Account account1 = new Account(1, "company", 1234);
        dbServiceUser.update(account1);
        Optional<Account> account2 = dbServiceUser.load(1, Account.class);
        assertThat(account2.get()).isEqualTo(account1);
        assertThat(account2.get()).isNotEqualTo(account);
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
             PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("Account table created");
    }


}
