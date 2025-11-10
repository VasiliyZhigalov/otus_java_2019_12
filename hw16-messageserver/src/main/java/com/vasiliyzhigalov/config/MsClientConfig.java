package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.MessageSystem;
import com.vasiliyzhigalov.messagesystem.MessageSystemImpl;
import com.vasiliyzhigalov.messagesystem.MsClient;
import com.vasiliyzhigalov.messagesystem.MsClientImpl;
import com.vasiliyzhigalov.serverSocket.ClientSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class MsClientConfig {
    private final ClientSocket clientSocket;
    private final MessageSystem messageSystem;
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    @Value("${front_server.port}")
    private int frontendPort;
    @Value("${front_server.host}")
    private String frontendHost;
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    @Value("${db_server.port}")
    private int databasePort;
    @Value("${db_server.host}")
    private String databaseHost;


    public MsClientConfig(ClientSocket clientSocket, @Lazy MessageSystem messageSystem) {
        this.clientSocket = clientSocket;
        this.messageSystem = messageSystem;
    }

    @Bean
    public MsClient frontendMsClient() {
        MsClient msClientFront = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem,
                clientSocket,
                frontendHost,
                frontendPort);
        return msClientFront;
    }


    @Bean
    public MsClient databaseMsClient() {
        MsClient msClientFront = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem,
                clientSocket,
                databaseHost,
                databasePort);
        return msClientFront;
    }


}
