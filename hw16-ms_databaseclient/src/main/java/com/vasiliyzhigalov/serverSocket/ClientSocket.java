package com.vasiliyzhigalov.serverSocket;

import com.vasiliyzhigalov.messagesystem.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Component
public class ClientSocket {
    private static Logger logger = LoggerFactory.getLogger(ClientSocket.class);

    @Value("${message_server.port}")
    private int msPort;
    @Value("${message_server.host}")
    private String msHost;


    public void sendMessage(Message message) {
        try (Socket clientSocket = new Socket(msHost, msPort);
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
            logger.info("Send to MS  message: {}", message);
            out.writeObject(message);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }
}