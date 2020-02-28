package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.ATMСellOverflowException;

import java.util.List;

public interface CashBox {
    /**
     * @return Возвращает остаток в кассе ATM
     */
    int getBalance();

    /**
     * @param cash список с банкнотами
     * @throws ATMСellOverflowException переполнение ячейки кассы ATM
     */
    void addCash(List<Banknote> cash) throws ATMСellOverflowException;

    /**
     * @param sum запрашиваемая сумма
     * @return список банкнот
     * @throws ATMImpossibleGiveAmountException невозможно выдать данную сумму. Либо недостаточно средств в кассе,
     *                                          либо сумма не кратная номиналу банкнот в ячейках
     */
    List<Banknote> getCash(int sum) throws ATMImpossibleGiveAmountException;
}
