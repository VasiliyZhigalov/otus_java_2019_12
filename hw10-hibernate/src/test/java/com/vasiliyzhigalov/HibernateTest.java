package com.vasiliyzhigalov;

import com.vasiliyzhigalov.core.dao.UserDao;
import com.vasiliyzhigalov.core.model.AddressDataSet;
import com.vasiliyzhigalov.core.model.PhoneDataSet;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.service.DBServiceUser;
import com.vasiliyzhigalov.core.service.DbServiceUserImpl;
import com.vasiliyzhigalov.hibernate.HibernateUtils;
import com.vasiliyzhigalov.hibernate.dao.UserDaoHibernate;
import com.vasiliyzhigalov.hibernate.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис должен ")

public class HibernateTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";
    private SessionFactory sessionFactory;
    private UserDao userDaoHibernate;
    private SessionManagerHibernate sessionManagerHibernate;
    private DBServiceUser dbServiceUser;


    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
        dbServiceUser = new DbServiceUserImpl(userDaoHibernate);
    }

    @Test
    @DisplayName("создать таблицы: users, addresses, phone, добавить запись user1,каскадно дбавить записи address и phones, загрузить user11 по id. user11 дожен быть равен user1")
    public void HibernateTest() {
        AddressDataSet address = new AddressDataSet("Lizukova");
        Set<PhoneDataSet> phoneList = new HashSet<>();
        phoneList.add(new PhoneDataSet("01"));
        phoneList.add(new PhoneDataSet("02"));
        User user1 = new User(address, phoneList, "John Smith", 45);
        long userId = dbServiceUser.saveUser(user1);
        Optional<User> user11 = dbServiceUser.findById(userId);
        assertThat(user11).isNotEmpty().get().isEqualTo(user1);
    }
}