package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.FrontMsClient;
import com.vasiliyzhigalov.messagesystem.MessageType;
import com.vasiliyzhigalov.messagesystem.MsClient;
import com.vasiliyzhigalov.serverSocket.ClientSocket;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import com.vasiliyzhigalov.services.frontendservises.handlers.FindAllResponseHandler;
import com.vasiliyzhigalov.services.frontendservises.handlers.SaveUserResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MsClientConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    private final ClientSocket clientSocket;
    private final FrontendService frontendService;

    public MsClientConfig(ClientSocket clientSocket, FrontendService frontendService) {
        this.clientSocket = clientSocket;
        this.frontendService = frontendService;
    }

    @Bean
    public MsClient getFrontendMsClient() throws IOException {
        MsClient msClientFront = new FrontMsClient(FRONTEND_SERVICE_CLIENT_NAME, clientSocket);
        msClientFront.addHandler(MessageType.ALL_USERS, new FindAllResponseHandler(frontendService));
        msClientFront.addHandler(MessageType.SAVE_USER, new SaveUserResponseHandler(frontendService));
        return msClientFront;
    }

}
