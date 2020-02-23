package com.vasiliyzhigalov.atm.exceptions;

public class ATMСellOverflowException extends ATMException {
    String message;

    public ATMСellOverflowException(String message) {
        super(message);
        this.message = message;
    }
}
