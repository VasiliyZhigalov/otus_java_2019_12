package com.vasiliyzhigalov.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
