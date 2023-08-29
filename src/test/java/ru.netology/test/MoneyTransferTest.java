package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV2;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.generateValidBalance;
import static ru.netology.data.DataHelper.getAuthInfo;

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
    var firstCardInfo = DataHelper.getFirstCartInfo();
    var secondCardInfo = DataHelper. getSecondCartInfo();
    var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    var amount = generateValidBalance(firstCardBalance);
    var expectedBalanceFirstCard = firstCardBalance - amount;
    var expectedBalanceSecondCard = secondCardBalance + amount;
    var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
    var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }
}

