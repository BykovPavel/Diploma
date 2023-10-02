package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.getCreditStatus;

public class CreditTests {
    private StartPage startPage;
    private CreditPage creditPage;

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
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkSuccessfulNotification();
        val expected = DataHelper.getFirstCardStatus();
        val actual = getCreditStatus();
        assertEquals(expected, actual);
    }

    //empty fields
    @Test
    void shouldDenyEmptyFields() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getEmptyCard();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val holder = DataHelper.getEmptyHolder();
        val Cvv = DataHelper.getEmptyCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkMandatoryFieldError();
    }

    //card
    @Test
    void shouldDenyEmptyCardField() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getEmptyCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenySecondCard() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getSecondCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkSuccessfulNotification();
        val expected = DataHelper.getSecondCardStatus();
        val actual = SQLHelper.getCreditStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyInvalidCard() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getInvalidCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkErrorNotification();
    }

    @Test
    void shouldDeny15DigitsCard() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getShortDigitsCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDeny1DigitCard() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getOneDigitCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextCard() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getContainingTextCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    //data
    @Test
    void shouldDenyMonthOver12() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getThirteenthMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongCardExpirationError();
    }

    @Test
    void shouldDenyMonthNull() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getNullMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongCardExpirationError();
    }

    @Test
    void shouldDenyWrongFormatMonth() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextMonth() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getContainingTextMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyEmptyYear() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyInvalidYear() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkCardExpiredError();
    }

    @Test
    void shouldDenyInvalidFormatYear() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextYear() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getContainingTextYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    //holder
    @Test
    void shouldDenyEmptyHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getEmptyHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyHolderOnlyName() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getOnlyName();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyLowercaseHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getLowercaseLettersHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyHolderWithSurname() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getWithSurnameHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyCyrillicHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getRussianHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyMixedHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getMixedHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyDigitsHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getDigitsHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    @Test
    void shouldDenyCharsHolder() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getCharsHolder();
        val Cvv = DataHelper.getValidCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }

    //cvv
    @Test
    void shouldDenyEmptyCvv() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getEmptyCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkMandatoryFieldError();
    }

    @Test
    void shouldDenyShortCvv() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getShortCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkWrongFormatError();
    }

    @Test
    void shouldDenyTextCvv() {
        creditPage = startPage.requestCredit();
        val cardNumber = DataHelper.getFirstCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val Cvv = DataHelper.getTextCvv();
        creditPage.fillForm(cardNumber, month, year, holder, Cvv);
        creditPage.checkInvalidCharactersError();
    }
}
