package com.vasiliyzhigalov.department;

import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.atm.ATMImpl;
import com.vasiliyzhigalov.department.atm.Memento;
import com.vasiliyzhigalov.department.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentATMImp implements DepartmentATM {
    private Map<ATM, Memento> atms;
    private String currency;
    private int balance;
    private Command command;

    public void setBalanceAllATM(int balance) {
        this.balance = balance;
    }

    public DepartmentATMImp() {
        this.atms = new HashMap<>();
        currency = "RUR";
    }

    @Override
    public Integer getBalanceAllATM() {
        command = new GetBalanceCommand(this);
        command.execute();
        return balance;
    }

    @Override
    public void createATM() {
        atms.put(config(), null);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Map<ATM, Memento> getATMs() {
        return atms;
    }

    @Override
    public Memento getBackup(ATM atm) {
        return atms.get(atm);
    }

    @Override
    public void setRandomStateAllATM() {
        command = new SetRandomStateAllATMCommand(this);
        command.execute();
    }


    @Override
    public void saveStateAllATM() {
        command = new MakeBackupCommand(this);
        command.execute();
    }

    @Override
    public void restoreStateAllATM() {
        command = new UndoCommand(this);
        command.execute();
    }

    //искуственный метод для реализации паттерна factory method
    private ATM config() {
        if (currency.contains("RUR")) {
            return new ATMImpl();
        } else {
            return new ATMImpl();
        }
    }

}
