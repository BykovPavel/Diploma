package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.BuyPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyTests {
    private StartPage startPage;
    private BuyPage buyPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        startPage = open("http://localhost:8080/", StartPage.class);
    }

    @AfterEach
    void cleanDB() {
        SQLHelper.clearDB();
    }

    @Test
    void shouldBuyWithValidData() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkSuccessfulNotification();
        val expected = DataHelper.getFirstCardStatus();
        val actual = SQLHelper.getBuyStatus();
        assertEquals(expected, actual);
    }

    //empty fields
    @Test
    void shouldDenyEmptyFields() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getEmptyCard();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val holder = DataHelper.getEmptyHolder();
        val Cvv = DataHelper.getEmptyCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkMandatoryFieldError();
    }

    //card
    @Test
    void shouldDenyEmptyCardField() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getEmptyCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenySecondCard() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getSecondCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkSuccessfulNotification();
        val expected = DataHelper.getSecondCardStatus();
        val actual = SQLHelper.getBuyStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyInvalidCard() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getInvalidCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkErrorNotification();
    }

    @Test
    void shouldDeny15DigitsCard() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getShortDigitsCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDeny1DigitCard() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getOneDigitCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextCard() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getContainingTextCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    //data
    @Test
    void shouldDenyMonthOver12() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getThirteenthMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongCardExpirationError();
    }

    @Test
    void shouldDenyMonthNull() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getNullMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongCardExpirationError();
    }

    @Test
    void shouldDenyWrongFormatMonth() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextMonth() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getContainingTextMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyEmptyYear() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyInvalidYear() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkCardExpiredError();
    }

    @Test
    void shouldDenyInvalidFormatYear() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextYear() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getContainingTextYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    //holder
    @Test
    void shouldDenyEmptyHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getEmptyHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyHolderOnlyName() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getOnlyName();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyLowercaseHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getLowercaseLettersHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyHolderWithSurname() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getWithSurnameHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyCyrillicHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getRussianHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyMixedHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getMixedHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyDigitsHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getDigitsHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyCharsHolder() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getCharsHolder();
        val Cvv = DataHelper.getValidCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }

    //cvv
    @Test
    void shouldDenyEmptyCvv() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getEmptyCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyShortCvv() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getShortCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextCvv() {
        buyPage = startPage.payWithCard();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getTextCvv();
        buyPage.fillForm(cardNumber, month, year, holder, Cvv);
        buyPage.checkInvalidCharactersError();
    }
}
