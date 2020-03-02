package com.vasiliyzhigalov.department.commands;

import com.vasiliyzhigalov.department.DepartmentATM;
import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.atm.Memento;

import java.util.List;

public abstract class Command {
    protected DepartmentATM department;

    public Command(DepartmentATM department) {
        this.department = department;
    }

    public abstract void execute();
}
