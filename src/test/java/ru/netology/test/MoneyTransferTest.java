package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardNumber;
import static ru.netology.data.DataHelper.getSecondCardNumber;
import static ru.netology.page.DashboardPage.pushFirstCardButton;
import static ru.netology.page.DashboardPage.pushSecondCardButton;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val cardBalance = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransFromCard1ToCard2() {
        int amount = 8000;
        val cardBalance = new DashboardPage();
        val firstCardBalanceStart = cardBalance.getFirstCardBalance();
        val secondCardBalanceStart = cardBalance.getSecondCardBalance();
        val transactionPage = pushSecondCardButton();
        transactionPage.transferMoney(amount, getFirstCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart - amount;
        val secondCardBalanceFinish = secondCardBalanceStart + amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalance.getSecondCardBalance());
    }

    @Test
    public void shouldTransFromCard2ToCard1() {
        int amount = 4500;
        val cardBalance = new DashboardPage();
        val firstCardBalanceStart = cardBalance.getFirstCardBalance();
        val secondCardBalanceStart = cardBalance.getSecondCardBalance();
        val transactionPage = pushFirstCardButton();
        transactionPage.transferMoney(amount, getSecondCardNumber());
        val firstCardBalanceFinish = firstCardBalanceStart + amount;
        val secondCardBalanceFinish = secondCardBalanceStart - amount;
        assertEquals(firstCardBalanceFinish, cardBalance.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, cardBalance.getSecondCardBalance());
    }
}