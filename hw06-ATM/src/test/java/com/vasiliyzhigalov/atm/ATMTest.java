package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.UnidentifiedBanknoteException;
import com.vasiliyzhigalov.atm.exceptions.СellOverflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис ATM должен ")
class ATMTest {
    private ATM atm = new ATM();
    private List<Banknote> cash = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        cash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        cash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        cash.add(BanknoteCell.BANKNOTE_CELL100.getBanknote());
        cash.add(BanknoteCell.BANKNOTE_CELL100.getBanknote());
        cash.add(BanknoteCell.BANKNOTE_CELL500.getBanknote());
    }

    @DisplayName("добавить список из банкнот и получить остаток 730 ")
    @Test
    void shouldAddCashAndGetBalance730() throws UnidentifiedBanknoteException, СellOverflowException {
        atm.add(cash);
        assertThat(atm.getBalance()).isEqualTo(730);
    }

    @DisplayName("выкинуть исключение UnidentifiedBanknoteException если попалась банкнота не из BanknoteCell")
    @Test
    void shouldThrowUnidentifiedBanknoteExceptionWhenAddBanknoteNotFromBanknoteCells() {
        cash.add(new Banknote("USD100", 100));
        Exception e = assertThrows(UnidentifiedBanknoteException.class, () -> atm.add(cash));
        assertThat(e.getMessage()).contains("неизвестная банкнота");
    }

    @DisplayName("выкинуть исключение СellOverflowException если добавть больше емкости ячеек кассы")
    @Test
    void shouldThrowСellOverflowExceptionWhenAddMoreCapacity() throws СellOverflowException, UnidentifiedBanknoteException {
        for (int i = 0; i < 11; i++) {
            cash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        }
        Exception e = assertThrows(СellOverflowException.class, () -> atm.add(cash));
        assertThat(e.getMessage()).contains("инкасация");
    }

    @DisplayName("выдать две банкноты (сотку и 2 десятки) при запросе 120")
    @Test
    void shouldGive2BanknotesWhenGetCash120() throws UnidentifiedBanknoteException, СellOverflowException, ImpossibleGiveAmountException {
        atm.add(cash);
        List<Banknote> actualCash = new ArrayList<>();
        actualCash.add(BanknoteCell.BANKNOTE_CELL100.getBanknote());
        actualCash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        actualCash.add(BanknoteCell.BANKNOTE_CELL10.getBanknote());
        assertThat(atm.getCash(120)).isEqualTo(actualCash);
    }
    @DisplayName("выкинуть исключение ImpossibleGiveAmountException если сумма больше остатка ")
    @Test
    void shouldThrowImpossibleGiveAmountExceptionWhenGetCashMoreBalance() throws UnidentifiedBanknoteException, СellOverflowException  {
        atm.add(cash);
        Exception e = assertThrows(ImpossibleGiveAmountException.class, () -> atm.getCash(1000));
        assertThat(e.getMessage()).contains("Недостаточно");
    }
    @DisplayName("выкинуть исключение ImpossibleGiveAmountException если запрашиваемая сумму невозможно выдать имеющимися купюрами")
    @Test
    void shouldThrowImpossibleGiveAmountExceptionWhenGetCashImpossibleGive() throws UnidentifiedBanknoteException, СellOverflowException  {
        atm.add(cash);
        Exception e = assertThrows(ImpossibleGiveAmountException.class, () -> atm.getCash(111));
        assertThat(e.getMessage()).contains("Невозможно выдать данную сумму имеющимися банкнотами");
    }
}