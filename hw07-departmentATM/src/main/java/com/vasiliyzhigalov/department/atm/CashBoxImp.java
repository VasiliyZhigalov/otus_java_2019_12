package com.vasiliyzhigalov.department.atm;

import com.vasiliyzhigalov.department.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.department.atm.exceptions.ATMСellOverflowException;

import java.util.*;

public class CashBoxImp implements CashBox, Cloneable {
    private Map<Banknote, Integer> banknoteCells = new TreeMap<>();
    private static final int MAX_NUMBER_OF_BANKNOTES_IN_CELL = 10;

    public CashBoxImp() {
        for (Banknote banknote : Banknote.values()) {
            banknoteCells.put(banknote, 0);
        }
    }

    public CashBoxImp(CashBoxImp cashBox) {
        for (Banknote banknote : Banknote.values()) {
            banknoteCells.put(banknote, cashBox.banknoteCells.get(banknote));
        }
    }
//prototype
    @Override
    public CashBoxImp clone() {
        return new CashBoxImp(this);
    }

    @Override
    public int getBalance() {
        return banknoteCells.keySet().stream().mapToInt(banknote -> banknoteCells.get(banknote) * banknote.getValue()).sum();
    }

    @Override
    public void addCash(List<Banknote> cash) throws ATMСellOverflowException {
        if (cash == null)
            throw new NullPointerException("cash");
        for (Banknote banknote : cash) {
            int quantityInCell = banknoteCells.get(banknote);
            if (quantityInCell == MAX_NUMBER_OF_BANKNOTES_IN_CELL) {
                throw new ATMСellOverflowException("ячейка с банкнотами '" + banknote + "' заполнена, требуется инкасация");
            }
            banknoteCells.put(banknote, banknoteCells.get(banknote) + 1);
        }
    }

    @Override
    public List<Banknote> getCash(int sum) throws ATMImpossibleGiveAmountException {
        if (sum < 0)
            throw new IllegalArgumentException("sum");
        if (sum > getBalance())
            throw new ATMImpossibleGiveAmountException("Недостаточно средсв");
        List<Banknote> resultCash = new ArrayList<>();
        List<Banknote> banknoteCellsList = new ArrayList<>(banknoteCells.keySet());
        banknoteCellsList.sort(Collections.reverseOrder());
        for (Banknote banknote : banknoteCellsList) {
            int quantityInCell = banknoteCells.get(banknote);
            if (quantityInCell != 0) {
                int quantity = Math.min(sum / banknote.getValue(), quantityInCell);
                for (int i = 0; i < quantity; i++) {
                    resultCash.add(banknote);
                }
                sum = sum - quantity * banknote.getValue();
            }
        }
        if (sum != 0) {
            throw new ATMImpossibleGiveAmountException("Невозможно выдать данную сумму имеющимися банкнотами");
        }
        return resultCash;
    }

    @Override
    public String toString() {
        return "CashBoxImp{" +
                "banknoteCells=" + banknoteCells +
                '}' + "balance " + this.getBalance();
    }
}
