package com.vasiliyzhigalov.department.commands;

import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.DepartmentATM;

public class UndoCommand extends Command {

    public UndoCommand(DepartmentATM department) {
        super(department);
    }

    private void restore() {
        for (ATM atm : department.getATMs().keySet()) {
        atm.restore(department.getBackup(atm));
        }
    }
    @Override
    public void execute() {
        this.restore();
    }
}
