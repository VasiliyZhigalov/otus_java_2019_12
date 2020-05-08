package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.MessageSystem;
import com.vasiliyzhigalov.messagesystem.MsClient;
import com.vasiliyzhigalov.messagesystem.MsClientImpl;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import com.vasiliyzhigalov.services.frontendservises.FrontendServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsClientConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private final MessageSystem messageSystem;

    public MsClientConfig(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Bean(name = "databaseMsClient")
    MsClient getDatabaseMsClient() {
        return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean(name = "frontendMsClient")
    MsClient getFrontendMsClient() {
        return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
    }

    @Bean
    FrontendService getFrontendService() {
        MsClient frontendMsClient = getFrontendMsClient();
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }
}
