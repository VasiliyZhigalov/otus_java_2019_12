package com.vasiliyzhigalov.atm;

import com.vasiliyzhigalov.atm.exceptions.ATMImpossibleGiveAmountException;
import com.vasiliyzhigalov.atm.exceptions.ATMСellOverflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис ATM должен ")
class ATMTest {
    private ATMImpl atm = new ATMImpl();
    private List<Banknote> cash = new ArrayList<>();

    @BeforeEach
    void setUp() {
        atm.addCashBox();
        cash.add(Banknote.ten);
        cash.add(Banknote.ten);
        cash.add(Banknote.ten);
        cash.add(Banknote.ten);
        cash.add(Banknote.fifty);
        cash.add(Banknote.fifty);
        cash.add(Banknote.fiveHundred);
        cash.add(Banknote.hundred);
    }

    @DisplayName("добавить список из банкнот и получить остаток 740 ")
    @Test
    void shouldAddCashAndGetBalance740() throws  ATMСellOverflowException {
        atm.addCash(cash);
        assertThat(atm.getBalance()).isEqualTo(740);
    }

    @DisplayName("выкинуть исключение СellOverflowException если добавть больше емкости ячеек кассы")
    @Test
    void shouldThrowСellOverflowExceptionWhenAddMoreCapacity() throws ATMСellOverflowException {
        for (int i = 0; i < 11; i++) {
            cash.add(Banknote.hundred);
        }
        Exception e = assertThrows(ATMСellOverflowException.class, () -> atm.addCash(cash));
        assertThat(e.getMessage()).contains("инкасация");
    }

    @DisplayName("выдать две банкноты (100 и 2 десятки) при запросе 120")
    @Test
    void shouldGive2BanknotesWhenGetCash120() throws ATMСellOverflowException, ATMImpossibleGiveAmountException {
        atm.addCash(cash);
        List<Banknote> expectedCash = new ArrayList<>();
        expectedCash.add(Banknote.hundred);
        expectedCash.add(Banknote.ten);
        expectedCash.add(Banknote.ten);
        assertThat(atm.getCash(120)).isEqualTo(expectedCash);
    }
    @DisplayName("выкинуть исключение ImpossibleGiveAmountException если сумма больше остатка ")
    @Test
    void shouldThrowImpossibleGiveAmountExceptionWhenGetCashMoreBalance() throws ATMСellOverflowException {
        atm.addCash(cash);
        Exception e = assertThrows(ATMImpossibleGiveAmountException.class, () -> atm.getCash(1000));
        assertThat(e.getMessage()).contains("Недостаточно");
    }
    @DisplayName("выкинуть исключение ImpossibleGiveAmountException если запрашиваемая сумму невозможно выдать имеющимися купюрами")
    @Test
    void shouldThrowImpossibleGiveAmountExceptionWhenGetCashImpossibleGive() throws ATMСellOverflowException {
        atm.addCash(cash);
        Exception e = assertThrows(ATMImpossibleGiveAmountException.class, () -> atm.getCash(111));
        assertThat(e.getMessage()).contains("Невозможно выдать данную сумму имеющимися банкнотами");
    }

}
