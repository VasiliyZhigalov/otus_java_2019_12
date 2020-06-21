package com.vasiliyzhigalov.messagesystem;

import com.vasiliyzhigalov.app.common.Serializers;
import com.vasiliyzhigalov.serverSocket.ClientSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FrontMsClient implements MsClient {

    private final String name;
    private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();
    private final ClientSocket clientSocket;
    private static Logger logger = LoggerFactory.getLogger(FrontMsClient.class);


    public FrontMsClient(String name, ClientSocket clientSocket) {
        this.name = name;
        this.clientSocket = clientSocket;
    }

    @Override
    public void addHandler(MessageType type, RequestHandler requestHandler) {
        this.handlers.put(type.getValue(), requestHandler);
    }

    @Override
    public boolean sendMessage(Message msg) {
        logger.info("Frontend Client send message: {} to Ms", msg);
        clientSocket.sendMessage(msg);
        return true;
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlers.get(msg.getType());
            if (requestHandler != null) {
                requestHandler.handle(msg).ifPresent(this::sendMessage);
            } else {
                logger.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, null, msgType.getValue(), Serializers.serialize(data));
    }
}
