package com.vasiliyzhigalov.core.dao;

import java.sql.SQLException;
import java.util.Optional;

public interface EntityDAO<T> {
    void create(T objectData);

    void update(T objectData);

    Optional<T> load(long id, Class<T> clazz) throws SQLException;
}
