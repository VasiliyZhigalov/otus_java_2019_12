package com.vasiliyzhigalov.department.atm;

import com.vasiliyzhigalov.department.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.department.atm.exceptions.ATMСellOverflowException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ATMImpl implements ATM {
    private CashBox cashBox;

    public ATMImpl() {
        this.cashBox = new CashBoxImp();
    }

    @Override
    public void addCashBox() {
        if (cashBox == null)
            cashBox = new CashBoxImp();
    }

    @Override
    public void setCashBox(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    @Override
    public CashBox getCashBox() {
        return cashBox;
    }

    @Override
    public int getBalance() {
        return cashBox.getBalance();
    }

    @Override
    public void addCash(List<Banknote> cash) throws ATMСellOverflowException {
        cashBox.addCash(cash);
    }

    @Override
    public List<Banknote> getCash(int sum) throws ATMImpossibleGiveAmountException {
        return cashBox.getCash(sum);
    }

    @Override
    public void setRandomState() throws ATMСellOverflowException {
        List<Banknote> cash = new ArrayList<>();
        Random rnd = new Random();
        int MAX_NUMBER_OF_BANKNOTES = 5;
        for (Banknote banknote : Banknote.values()) {
            int values = rnd.nextInt(MAX_NUMBER_OF_BANKNOTES);
            for (int i = 0; i < values; i++) {
                cash.add(banknote);
            }
        }
        this.addCash(cash);

    }

    @Override
    public Memento save() {
        return new Memento(cashBox.clone());
    }

    @Override
    public void restore(Memento memento) {
        cashBox = memento.getCashBox();
    }
}