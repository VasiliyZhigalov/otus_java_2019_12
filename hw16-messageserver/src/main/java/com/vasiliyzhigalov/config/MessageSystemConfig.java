package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.MessageSystem;
import com.vasiliyzhigalov.messagesystem.MessageSystemImpl;
import com.vasiliyzhigalov.messagesystem.MsClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class MessageSystemConfig {
    @Qualifier("frontendMsClient")
    private final MsClient frontendMsClient;
    @Qualifier("databaseMsClient")
    private final MsClient databaseMsClient;

    public MessageSystemConfig(MsClient frontendMsClient, MsClient databaseMsClient) {
        this.frontendMsClient = frontendMsClient;
        this.databaseMsClient = databaseMsClient;
    }

    @Bean
    MessageSystem getMessageSystem() {
        MessageSystemImpl messageSystem = new MessageSystemImpl();
        messageSystem.addClient(frontendMsClient);
        messageSystem.addClient(databaseMsClient);
        return messageSystem;
    }
}
