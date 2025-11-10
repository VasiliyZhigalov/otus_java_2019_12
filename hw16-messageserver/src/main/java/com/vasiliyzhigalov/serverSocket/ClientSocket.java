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

    public void sendMessage(Message message, String host, int port) {
        try (Socket clientSocket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
            logger.info("Send to {}  message: {}", message.getTo(), message);
            out.writeObject(message);
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

}