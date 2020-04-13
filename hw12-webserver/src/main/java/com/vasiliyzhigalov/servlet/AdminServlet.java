package com.vasiliyzhigalov.servlet;

import com.vasiliyzhigalov.model.User;
import com.vasiliyzhigalov.services.TemplateProcessor;
import com.vasiliyzhigalov.services.dbservice.DbServiceUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private final DbServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public AdminServlet(DbServiceUser dbServiceUser, TemplateProcessor templateProcessor) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        List<User> users = dbServiceUser.findAll();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("users", users);
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Map<String, Object> paramsMap = new HashMap<>();
        if(!req.getParameter("name").isEmpty() && !req.getParameter("login").isEmpty() && !req.getParameter("password").isEmpty()){
            User newUser = new User(0L, req.getParameter("name"), req.getParameter("login"), req.getParameter("password"));
            dbServiceUser.saveUser(newUser);
        } else {
            paramsMap.put("attention","Заполните все поля!");
        }
        List<User> users = dbServiceUser.findAll();
        paramsMap.put("users", users);
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }
}
