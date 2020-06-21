package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.MessageSystem;
import com.vasiliyzhigalov.messagesystem.MessageType;
import com.vasiliyzhigalov.messagesystem.MsClient;
import com.vasiliyzhigalov.messagesystem.MsClientImpl;
import com.vasiliyzhigalov.services.dbservices.DbServiceUser;
import com.vasiliyzhigalov.services.dbservices.handlers.FindAllRequestHandler;
import com.vasiliyzhigalov.services.dbservices.handlers.SaveUserRequestHandler;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import com.vasiliyzhigalov.services.frontendservises.handlers.FindAllResponseHandler;
import com.vasiliyzhigalov.services.frontendservises.handlers.SaveUserResponseHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MsClientSetup {
    private final MsClient databaseMsClient;
    private final MsClient frontendMsClient;
    private final MessageSystem messageSystem;
    private final DbServiceUser dbServiceUser;
    private final FrontendService frontendService;

    public MsClientSetup(@Qualifier("databaseMsClient") MsClient databaseMsClient,
                         @Qualifier("frontendMsClient") MsClient frontendMsClient,
                         MessageSystem messageSystem, DbServiceUser dbServiceUser,
                         FrontendService frontendService) {
        this.databaseMsClient = databaseMsClient;
        this.frontendMsClient = frontendMsClient;
        this.messageSystem = messageSystem;
        this.dbServiceUser = dbServiceUser;
        this.frontendService = frontendService;
    }

    @PostConstruct
    public void postConstructMsClientSetup() {
        databaseMsClient.addHandler(MessageType.ALL_USERS, new FindAllRequestHandler(dbServiceUser));
        databaseMsClient.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(dbServiceUser));
        messageSystem.addClient(databaseMsClient);

        frontendMsClient.addHandler(MessageType.ALL_USERS, new FindAllResponseHandler(frontendService));
        frontendMsClient.addHandler(MessageType.SAVE_USER, new SaveUserResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
    }
}
