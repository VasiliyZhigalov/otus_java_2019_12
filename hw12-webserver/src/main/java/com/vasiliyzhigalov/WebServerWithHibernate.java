package com.vasiliyzhigalov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vasiliyzhigalov.dao.HibernateUserDao;
import com.vasiliyzhigalov.dao.UserDao;
import com.vasiliyzhigalov.model.User;
import com.vasiliyzhigalov.server.UsersWebServer;
import com.vasiliyzhigalov.server.UsersWebServerWithFilterBasedSecurity;
import com.vasiliyzhigalov.services.TemplateProcessor;
import com.vasiliyzhigalov.services.TemplateProcessorImpl;
import com.vasiliyzhigalov.services.UserAuthService;
import com.vasiliyzhigalov.services.UserAuthServiceImpl;
import com.vasiliyzhigalov.services.dbservice.DbServiceUser;
import com.vasiliyzhigalov.services.dbservice.DbServiceUserImpl;
import com.vasiliyzhigalov.sessionmanager.SessionManagerHibernate;
import com.vasiliyzhigalov.util.HibernateUtils;
import org.hibernate.SessionFactory;


/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithHibernate {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";
    private static SessionFactory sessionFactory;

    public static void main(String[] args) throws Exception {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new HibernateUserDao(sessionManager);
        DbServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        new WebServerWithHibernate().addAdministrator(dbServiceUser);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);
        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT, authService, userDao, gson, templateProcessor, dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();
    }

    private void addAdministrator(DbServiceUser dbServiceUser) {
        User admin = new User(0L, "admin", "admin", "admin");
        dbServiceUser.saveUser(admin);
    }

}
