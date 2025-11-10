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

import java.util.Optional;

public class SaveUserRequestHandler implements RequestHandler {

    private DbServiceUser dbServiceUser;
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    public SaveUserRequestHandler(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        logger.info("add user :{}",user);
        long id = dbServiceUser.saveUser(user);
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.SAVE_USER.getValue(), Serializers.serialize(id)));
    }
}
