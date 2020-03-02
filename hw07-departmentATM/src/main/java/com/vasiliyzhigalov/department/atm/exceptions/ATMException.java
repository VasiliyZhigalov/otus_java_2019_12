package com.vasiliyzhigalov.department.atm.exceptions;

public abstract class ATMException extends Exception {
    public ATMException(String message) {
        super(message);
    }
}
