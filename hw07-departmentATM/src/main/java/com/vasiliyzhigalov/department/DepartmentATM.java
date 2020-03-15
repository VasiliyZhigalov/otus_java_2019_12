package com.vasiliyzhigalov.department;

import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.atm.Memento;

import java.util.List;
import java.util.Map;

public interface DepartmentATM {
    void setCurrency(String currency);

    void setBalanceAllATM(int balance);

    Integer getBalanceAllATM();

    void createATM();

    Map<ATM, Memento> getATMs();
    Memento getBackup(ATM atm);


    void setRandomStateAllATM();
    void saveStateAllATM();

    void restoreStateAllATM();


}
