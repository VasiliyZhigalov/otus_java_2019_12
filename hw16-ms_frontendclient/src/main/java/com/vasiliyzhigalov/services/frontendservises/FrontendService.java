package com.vasiliyzhigalov.services.frontendservises;

import com.vasiliyzhigalov.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void findAll(Consumer<List<User>> dataConsumer);

    void addUser(User user, Consumer<Long> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

