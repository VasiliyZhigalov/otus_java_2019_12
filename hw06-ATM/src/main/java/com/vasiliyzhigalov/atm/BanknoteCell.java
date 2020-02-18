package com.vasiliyzhigalov.atm;

import java.util.Comparator;

public enum BanknoteCell {
    BANKNOTE_CELL1000(new Banknote("тыща", 1000)),
    BANKNOTE_CELL500(new Banknote("пятихатка", 500)),
    BANKNOTE_CELL100(new Banknote("сотка", 100)),
    BANKNOTE_CELL10(new Banknote("десять", 10));


    BanknoteCell(Banknote banknote) {
        this.banknote = banknote;
    }

    private Banknote banknote;

    public Banknote getBanknote() {
        return banknote;
    }
}
