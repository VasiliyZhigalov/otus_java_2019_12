package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.ATMСellOverflowException;

import java.util.*;

public class CashBoxImp implements CashBox {
    private Map<Banknote, Integer> banknoteCells = new TreeMap<>();
    private static final int MAX_NUMBER_OF_BANKNOTES_IN_CELL = 10;

    public CashBoxImp() {
        for (Banknote banknote : Banknote.values()) {
            banknoteCells.put(banknote, 0);
        }
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
}
