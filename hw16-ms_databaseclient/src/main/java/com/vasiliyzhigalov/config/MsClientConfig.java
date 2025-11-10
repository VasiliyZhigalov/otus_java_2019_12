package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.DatabaseMsClient;
import com.vasiliyzhigalov.messagesystem.MessageType;
import com.vasiliyzhigalov.messagesystem.MsClient;
import com.vasiliyzhigalov.serverSocket.ClientSocket;
import com.vasiliyzhigalov.services.dbservices.DbServiceUser;
import com.vasiliyzhigalov.services.dbservices.handlers.FindAllRequestHandler;
import com.vasiliyzhigalov.services.dbservices.handlers.SaveUserRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsClientConfig {
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    private final ClientSocket clientSocket;
    private final DbServiceUser dbServiceUser;

    public MsClientConfig(ClientSocket clientSocket, DbServiceUser dbServiceUser) {
        this.clientSocket = clientSocket;
        this.dbServiceUser = dbServiceUser;
    }

    @Bean
    public MsClient getDatabaseMsClient()  {
        MsClient databaseMsClient = new DatabaseMsClient(DATABASE_SERVICE_CLIENT_NAME, clientSocket);
        databaseMsClient.addHandler(MessageType.ALL_USERS, new FindAllRequestHandler(dbServiceUser));
        databaseMsClient.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(dbServiceUser));
        return databaseMsClient;
    }

}
