package com.vasiliyzhigalov.testfamework;

public class AssertionFailedError extends AssertionError {
    String message;
    public AssertionFailedError(String detailMessage) {
        super(detailMessage);
        this.message = detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
