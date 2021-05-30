package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.test.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.test.DataGenerator.getRandomLogin;
import static ru.netology.test.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        val registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        val blockedUser = getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        val registeredUser = getRegisteredUser("active");
        val wrongLogin = getRandomLogin();

        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        val registeredUser = getRegisteredUser("active");
        val wrongPassword = getRandomPassword();

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if empty login")
    void shouldGetErrorIfEmptyLogin() {
        val registeredUser = getRegisteredUser("active");

        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if empty password")
    void shouldGetErrorIfEmptyPassword() {
        val registeredUser = getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}