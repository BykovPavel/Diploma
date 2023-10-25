package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getFirstCard() {
        return "4444 4444 4444 4441";
    }

    public static String getFirstCardStatus() {
        return "APPROVED";
    }

    public static String getSecondCard() {
        return "4444 4444 4444 4442";
    }

    public static String getSecondCardStatus() {
        return "DECLINED";
    }

    public static String getEmptyFieldValue() {
        return "";
    }

    public static String getInvalidCard() {
        return "5555 5555 5555 5555";
    }

    public static String getShortDigitsCard() {
        return "5555 5555 5555 555";
    }

    public static String getOneDigitCard() {
        return "5";
    }

    public static String getContainingTextCard() {
        return "пять";
    }

    public static String getValidMonth() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getThirteenthMonth() {
        return "13";
    }

    public static String getNullMonth() {
        return "00";
    }

    public static String getInvalidFormatMonth() {
        return "1";
    }

    public static String getContainingTextMonth() {
        return "Январь";
    }

    public static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidFormatYear() {
        return "1";
    }

    public static String getContainingTextYear() {
        return "Двадцатый";
    }

    public static String getValidHolder() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getRussianHolder() {
        return fakerRu.name().firstName() + " " + fakerRu.name().lastName();
    }

    public static String getMixedHolder() {
        return fakerRu.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getOnlyName() {
        return fakerEn.name().firstName();
    }

    public static String getLowercaseLettersHolder() {
        return "michael scott";
    }

    public static String getWithSurnameHolder() {
        return "Michael Scott Dwightovich";
    }

    public static String getDigitsHolder() {
        return "55555";
    }

    public static String getCharsHolder() {
        return "???";
    }

    public static String getValidCvv() {
        return fakerEn.number().digits(3);
    }

    public static String getShortCvv() {
        return "69";
    }

    public static String getTextCvv() {
        return "абв";
    }


}
