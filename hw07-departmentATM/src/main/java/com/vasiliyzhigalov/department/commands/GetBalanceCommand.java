package com.vasiliyzhigalov.department.commands;

import com.vasiliyzhigalov.department.DepartmentATM;
import com.vasiliyzhigalov.department.atm.ATM;


public class GetBalanceCommand extends Command {

    public GetBalanceCommand(DepartmentATM department) {
        super(department);
    }

    private void getBalanceAllATM() {
        int balance = 0;
        for (ATM atm : department.getATMs().keySet()) {
            balance += atm.getBalance();
        }
        department.setBalanceAllATM(balance);
    }

    @Override
    public void execute() {
        this.getBalanceAllATM();
    }
}
