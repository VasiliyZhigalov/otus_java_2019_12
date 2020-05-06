package com.vasiliyzhigalov.services.dbservices.handlers;

import com.vasiliyzhigalov.app.common.Serializers;
import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.messagesystem.Message;
import com.vasiliyzhigalov.messagesystem.MessageType;
import com.vasiliyzhigalov.messagesystem.RequestHandler;
import com.vasiliyzhigalov.services.dbservices.DbServiceUser;
import com.vasiliyzhigalov.services.dbservices.DbServiceUserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FindAllRequestHandler implements RequestHandler {
    private final DbServiceUser dbServiceUser;
    private static final Logger logger = LoggerFactory.getLogger(FindAllRequestHandler.class);

    public FindAllRequestHandler(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<User> users = dbServiceUser.findAll();
        logger.info("users:{}", users);
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.ALL_USERS.getValue(), Serializers.serialize(users)));
    }
}
