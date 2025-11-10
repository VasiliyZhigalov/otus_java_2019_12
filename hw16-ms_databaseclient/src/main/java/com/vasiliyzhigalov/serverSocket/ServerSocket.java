package com.vasiliyzhigalov.serverSocket;

import com.vasiliyzhigalov.messagesystem.Message;
import com.vasiliyzhigalov.messagesystem.MsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.net.Socket;

@Component
public class ServerSocket {
    private static Logger logger = LoggerFactory.getLogger(ServerSocket.class);
    @Value("${db_server.port}")
    private int dbServerPort;
    private final MsClient frontMsClient;

    public ServerSocket(MsClient frontMsClient) {
        this.frontMsClient = frontMsClient;
    }

    public void go() {
        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(dbServerPort)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connection");
                try (Socket clientSocket = serverSocket.accept()) {
                    clientHandler(clientSocket);
                }
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void clientHandler(Socket clientSocket) {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
            Message inMsg = (Message) in.readObject();
            logger.info("Received from MS message: {} ", inMsg);
            frontMsClient.handle(inMsg);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }


}
