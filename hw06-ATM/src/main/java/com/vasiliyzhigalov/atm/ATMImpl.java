package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.ATMСellOverflowException;

import java.util.List;

public class ATMImpl implements ATM {
    private CashBox cashBox;

    @Override
    public void addCashBox() {
        cashBox = new CashBoxImp();
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
}
