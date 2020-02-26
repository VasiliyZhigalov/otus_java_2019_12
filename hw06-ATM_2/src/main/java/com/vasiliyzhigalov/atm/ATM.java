package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.ATMСellOverflowException;

import java.util.List;

public interface ATM {
    void addCashBox();
    int getBalance();
    void addCash(List<Banknote> cash) throws ATMСellOverflowException;
    List <Banknote> getCash(int sum) throws ATMImpossibleGiveAmountException;
}
