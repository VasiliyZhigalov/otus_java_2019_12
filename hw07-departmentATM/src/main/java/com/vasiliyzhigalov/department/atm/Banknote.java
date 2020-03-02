package com.vasiliyzhigalov.department.atm;

public enum Banknote {
    ten(10),
    fifty(50),
    hundred(100),
    fiveHundred(500),
    thousand(1000);

    private int value;

    Banknote(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
