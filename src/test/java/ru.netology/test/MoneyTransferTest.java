package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV2;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
        var authInfo = getAuthInfo();
        var verificationPage = LoginPageV2.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromFirstCardToSecond() {
        var firstCardInfo = DataHelper.getFirstCartInfo(); // инфо о первой карте
        var secondCardInfo = DataHelper.getSecondCartInfo(); // инфо о второй карте
        var firstCardBalance = dashboardPage.getCardBalance(getFirstCartInfo()); //  поиск баланса по номеру карты, последние 4 цифры
        var secondCardBalance = dashboardPage.getCardBalance(getSecondCartInfo()); // поиск баланса по номеру карты, последние 4 цифры
        var amount = generateValidBalance(firstCardBalance); // генерирует валидный баланс
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo); // ищем карту по id, и нажимаем кнопку
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo); //перевод
// Сравнение полученных данных
        var expectedBalanceFirstCard = firstCardBalance - amount; //ожидаемый результат первой карты
        var expectedBalanceSecondCard = secondCardBalance + amount; // ожидаемы результат второй карты
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
}

