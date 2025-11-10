package com.vasiliyzhigalov.services.frontendservises.handlers;

import com.vasiliyzhigalov.app.common.Serializers;
import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.messagesystem.Message;
import com.vasiliyzhigalov.messagesystem.RequestHandler;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindAllResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(FindAllResponseHandler.class);
    private final FrontendService frontendService;

    public FindAllResponseHandler(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<User> users = (List<User>) Serializers.deserialize(msg.getPayload(), List.class);
        logger.info("users:{}", users);
        UUID sourceMessageId = msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
        frontendService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(users));
        return Optional.empty();
    }
}
