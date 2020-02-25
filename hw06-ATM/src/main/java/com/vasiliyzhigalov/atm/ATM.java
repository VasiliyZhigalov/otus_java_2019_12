package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.UnidentifiedBanknoteException;
import com.vasiliyzhigalov.atm.exceptions.СellOverflowException;

import java.util.*;

public class ATM {
    private static final int MAX_NUMBER_OF_BANKNOTES_IN_CELL = 10;
    private Map<BanknoteCell, Integer> cashCells = new TreeMap<>();

    public ATM() {
        for (BanknoteCell banknoteCell : BanknoteCell.values()) {
            cashCells.put(banknoteCell, 0);
        }
    }

    public int getBalance() {
        return cashCells.keySet().stream().mapToInt(bnc -> cashCells.get(bnc) * bnc.getBanknote().getValue()).sum();
    }

    public void add(List<Banknote> cash) throws UnidentifiedBanknoteException, СellOverflowException {
        if (cash == null)
            throw new NullPointerException("cash");
        for (Banknote banknote : cash) {
            banknoteCheck(banknote);
            for (BanknoteCell banknoteCell : cashCells.keySet()) {
                if (banknoteCell.getBanknote().equals(banknote)) {
                    cashCells.put(banknoteCell, cashCells.get(banknoteCell) + 1);
                }
            }
        }
    }

    private boolean banknoteCheck(Banknote banknote) throws UnidentifiedBanknoteException, СellOverflowException {
        for (BanknoteCell banknoteCell : cashCells.keySet()) {
            if (banknoteCell.getBanknote().equals(banknote)) {
                int numberInCell = cashCells.get(banknoteCell);
                if (numberInCell == MAX_NUMBER_OF_BANKNOTES_IN_CELL) {
                    throw new СellOverflowException("ячейка с банкнотами '" + banknoteCell.getBanknote().getName() + "' заполнена, требуется инкасация");
                }
                return true;
            }
        }
        throw new UnidentifiedBanknoteException("неизвестная банкнота :" + banknote.getName());
    }

    public List<Banknote> getCash(int sum) throws ImpossibleGiveAmountException {
        if (sum < 0)
            throw new IllegalArgumentException("sum");
        if(sum > getBalance())
            throw new ImpossibleGiveAmountException("Недостаточно средсв");
        List<Banknote> cash = new ArrayList<>();
        for (BanknoteCell banknoteCell : cashCells.keySet()) {
            int number = Math.min(sum / banknoteCell.getBanknote().getValue(), cashCells.get(banknoteCell));
            for (int index = 0; index < number; index++) {
                cash.add(banknoteCell.getBanknote());
            }
            sum = sum - number * banknoteCell.getBanknote().getValue();
        }
        if (sum != 0) {
            throw new ImpossibleGiveAmountException("Невозможно выдать данную сумму имеющимися банкнотами");
        }
        return cash;
    }
}
