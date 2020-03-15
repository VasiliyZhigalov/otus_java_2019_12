package com.vasiliyzhigalov.department.atm;

public class Memento {

    private CashBox cashBox;

    public Memento(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    @Override
    public String toString() {
        return "Memento{" +
                "cashBox=" + cashBox +
                '}';
    }
}
