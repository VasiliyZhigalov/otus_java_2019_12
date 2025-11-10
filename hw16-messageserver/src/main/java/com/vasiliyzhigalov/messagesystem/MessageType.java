package com.vasiliyzhigalov.messagesystem;

public enum MessageType {
    ALL_USERS("AllUsers"),
    SAVE_USER("AddUser");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
