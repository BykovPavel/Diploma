package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public StartPage() {
        heading.shouldBe(visible);
    }

    public BuyPage payWithCard() {
        buyButton.click();
        return new BuyPage();
    }

    public CreditPage requestCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
