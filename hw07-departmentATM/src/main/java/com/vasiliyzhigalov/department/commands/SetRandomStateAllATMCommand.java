package com.vasiliyzhigalov.department.commands;

import com.vasiliyzhigalov.department.DepartmentATM;
import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.atm.exceptions.ATMСellOverflowException;

public class SetRandomStateAllATMCommand extends Command {

    public SetRandomStateAllATMCommand(DepartmentATM department) {
        super(department);
    }

    private void  setRandomState() throws ATMСellOverflowException {
        for(ATM atm : department.getATMs().keySet()){
            atm.setRandomState();
        }
    }

    @Override
    public void execute() {
        try {
            this.setRandomState();
        } catch (ATMСellOverflowException e) {
            e.printStackTrace();
        }
    }
}
