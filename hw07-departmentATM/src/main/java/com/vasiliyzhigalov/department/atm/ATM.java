package com.vasiliyzhigalov.department.atm;


import com.vasiliyzhigalov.department.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.department.atm.exceptions.ATMСellOverflowException;

import java.util.List;

public interface ATM {

    void addCashBox();

    void setCashBox(CashBox cashBox);

    CashBox getCashBox();


    int getBalance();

    void addCash(List<Banknote> cash) throws ATMСellOverflowException;

    List<Banknote> getCash(int sum) throws ATMImpossibleGiveAmountException;

    void  setRandomState() throws ATMСellOverflowException;

    Memento save();

    void restore(Memento memento);

}
