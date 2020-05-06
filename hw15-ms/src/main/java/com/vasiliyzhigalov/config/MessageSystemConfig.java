package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.*;
import com.vasiliyzhigalov.services.dbservices.DbServiceUser;
import com.vasiliyzhigalov.services.dbservices.handlers.FindAllRequestHandler;
import com.vasiliyzhigalov.services.dbservices.handlers.SaveUserRequestHandler;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import com.vasiliyzhigalov.services.frontendservises.FrontendServiceImpl;
import com.vasiliyzhigalov.services.frontendservises.handlers.FindAllResponseHandler;
import com.vasiliyzhigalov.services.frontendservises.handlers.SaveUserResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSystemConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private MessageSystem messageSystem = new MessageSystemImpl();

    @Bean
    MsClient getDatabaseMsClient() {
        return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean
    MsClient getFrontendMsClient() {
        return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean
    FrontendService getFrontendService() {
        return new FrontendServiceImpl(getFrontendMsClient(), DATABASE_SERVICE_CLIENT_NAME);
    }

    @Bean
    MessageSystem getMessageSystem(DbServiceUser dbServiceUser, FrontendService frontendService) {
        MsClient databaseMsClient = getDatabaseMsClient();
        databaseMsClient.addHandler(MessageType.ALL_USERS, new FindAllRequestHandler(dbServiceUser));
        databaseMsClient.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(dbServiceUser));
        messageSystem.addClient(databaseMsClient);

        MsClient frontendMsClient = getFrontendMsClient();
        frontendMsClient.addHandler(MessageType.ALL_USERS, new FindAllResponseHandler(frontendService));
        frontendMsClient.addHandler(MessageType.SAVE_USER, new SaveUserResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
        return messageSystem;
    }
}
