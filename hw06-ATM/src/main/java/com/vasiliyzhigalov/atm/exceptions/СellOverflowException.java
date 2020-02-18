package com.vasiliyzhigalov.atm.exceptions;

public class СellOverflowException extends Exception {
    String message;

    public СellOverflowException(String message) {
        super(message);
        this.message = message;
    }
}
