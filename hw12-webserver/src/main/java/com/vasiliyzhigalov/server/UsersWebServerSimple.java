package com.vasiliyzhigalov.server;

import com.google.gson.Gson;
import com.vasiliyzhigalov.dao.UserDao;
import com.vasiliyzhigalov.helpers.FileSystemHelper;
import com.vasiliyzhigalov.services.TemplateProcessor;
import com.vasiliyzhigalov.services.dbservice.DbServiceUser;
import com.vasiliyzhigalov.servlet.AdminServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class UsersWebServerSimple implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final UserDao userDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final DbServiceUser dbServiceUser;

    public UsersWebServerSimple(int port, UserDao userDao, Gson gson, TemplateProcessor templateProcessor, DbServiceUser dbServiceUser) {
        this.userDao = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/admin"));


        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(dbServiceUser, templateProcessor)), "/admin");
        return servletContextHandler;
    }
}
