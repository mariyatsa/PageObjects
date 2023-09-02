package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.trim;

public class DashboardPage {
    private static final String balanceStart = "баланс: ";

    private static final String balanceFinish = " р.";

    private final SelenideElement heading = $("[data-test-id=dashboard]");

    private final ElementsCollection cards = $$(".list__item div");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String text;
        text = cards.findBy(text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);  // поиск баланса по номеру карты, последние 4 цифры
        // берет строку и преобразует ее в число, возвращает значени числа баланса
    }

    //public static int getCardBalance(int index) { поиск баланса по строкам
    //var text = cards.get(index).getText();
    //return extractBalance(text);}

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }

    private static int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}



