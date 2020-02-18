package com.vasiliyzhigalov.atm.exceptions;

public class UnidentifiedBanknoteException extends Exception {
    private String message;

    public UnidentifiedBanknoteException(String message) {
        super(message);
        this.message = message;
    }
}
