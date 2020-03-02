package com.vasiliyzhigalov.department;

import com.vasiliyzhigalov.department.atm.ATM;
import com.vasiliyzhigalov.department.atm.Banknote;
import com.vasiliyzhigalov.department.atm.exceptions.ATMСellOverflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис ATM должен ")
public class DepartmentATMTest {
    DepartmentATM departmentATM = new DepartmentATMImp();
    List<Banknote> cash;
    @BeforeEach
    void setUp() {
        departmentATM.createATM();
        departmentATM.createATM();
        departmentATM.setRandomStateAllATM();
        departmentATM.saveStateAllATM();
        cash = new ArrayList<>();
    }

    @DisplayName("должен вернуть 3 если добавим 1 АТМ")
    @Test
    void shouldGet3IfAdd1ATM() {
        departmentATM.createATM();
        assertThat(departmentATM.getATMs().size()).isEqualTo(3);
    }

    @DisplayName("должен вернуть общий баланс равный сумме балансов всех АТМ")
    @Test
    void shouldGetTotalBalanceEqualSumOfBalancesOfAllATMs() {
        int totalBalance = 0;
        for (ATM atm : departmentATM.getATMs().keySet())
            totalBalance += atm.getBalance();
        assertThat(departmentATM.getBalanceAllATM()).isEqualTo(totalBalance);
    }

    @DisplayName("должен вернуть начальное состояние баланса всех АТМ после изменения")
    @Test
    void shouldReturnInitStateOfTotalBalanceAfterChange() throws ATMСellOverflowException {
        departmentATM.saveStateAllATM();
        int startBalance = departmentATM.getBalanceAllATM();
        for(ATM atm : departmentATM.getATMs().keySet()){
            cash.add(Banknote.fifty);
            cash.add(Banknote.ten);
            atm.addCash(cash);
        }
        departmentATM.restoreStateAllATM();
        int currentBalance = departmentATM.getBalanceAllATM();

        assertThat(currentBalance).isEqualTo(startBalance);
    }


}
