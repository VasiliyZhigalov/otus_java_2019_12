package com.vasiliyzhigalov.services.frontendservises.handlers;

import com.vasiliyzhigalov.app.common.Serializers;
import com.vasiliyzhigalov.messagesystem.Message;
import com.vasiliyzhigalov.messagesystem.RequestHandler;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class SaveUserResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(SaveUserResponseHandler.class);
    private final FrontendService frontendService;

    public SaveUserResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }
    @Override
    public Optional<Message> handle(Message msg) {
        Long id = Serializers.deserialize(msg.getPayload(), Long.class);
        logger.info("add user id:{}", id);
        UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
        frontendService.takeConsumer(sourceMessageId, Long.class).ifPresent(consumer -> consumer.accept(id));
        return Optional.empty();
    }
}
