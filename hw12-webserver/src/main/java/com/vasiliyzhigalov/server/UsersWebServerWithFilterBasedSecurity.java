package com.vasiliyzhigalov.server;

import com.google.gson.Gson;
import com.vasiliyzhigalov.dao.UserDao;
import com.vasiliyzhigalov.services.TemplateProcessor;
import com.vasiliyzhigalov.services.UserAuthService;
import com.vasiliyzhigalov.services.dbservice.DbServiceUser;
import com.vasiliyzhigalov.servlet.AuthorizationFilter;
import com.vasiliyzhigalov.servlet.LoginServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


import java.util.Arrays;

public class UsersWebServerWithFilterBasedSecurity extends UsersWebServerSimple {
    private final UserAuthService authService;

    public UsersWebServerWithFilterBasedSecurity(int port,
                                                 UserAuthService authService,
                                                 UserDao userDao,
                                                 Gson gson,
                                                 TemplateProcessor templateProcessor, DbServiceUser dbServiceUser) {
        super(port, userDao, gson, templateProcessor, dbServiceUser);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
