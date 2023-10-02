package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyPage {
    private SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement holderField = $$("[class='input__control']").get(3);
    private SelenideElement cvvField = $("[placeholder='999']");

    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successfulNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement mandatoryFieldError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private SelenideElement invalidCharactersError = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement wrongCardExpirationError = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));


    public void fillForm(String cardNumber, String month, String year, String cardHolder, String cvv) {
        cardNumberField.sendKeys(cardNumber);
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        holderField.sendKeys(cardHolder);
        cvvField.sendKeys(cvv);
        continueButton.click();
    }

    public void checkErrorNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkSuccessfulNotification() {
        successfulNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkMandatoryFieldError() {
        mandatoryFieldError.shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

    public void checkWrongFormatError() {
        wrongFormatError.shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

    public void checkInvalidCharactersError() {
        invalidCharactersError.shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

    public void checkWrongCardExpirationError() {
        wrongCardExpirationError.shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

    public void checkCardExpiredError() {
        cardExpiredError.shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

}
