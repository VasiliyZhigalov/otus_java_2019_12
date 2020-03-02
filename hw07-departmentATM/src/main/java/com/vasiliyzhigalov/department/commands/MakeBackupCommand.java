package com.vasiliyzhigalov.department.commands;

import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.DepartmentATM;

public class MakeBackupCommand extends Command {
    public MakeBackupCommand(DepartmentATM department) {
        super(department);
    }
    private void save() {
        for (ATM atm : department.getATMs().keySet()) {
            department.getATMs().put(atm,atm.save());
        }
    }
    @Override
    public void execute() {
        save();
    }
}
