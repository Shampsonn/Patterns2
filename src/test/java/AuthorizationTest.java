import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @BeforeEach
    void setup(){
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessfulRegUser(){
        Generator.RegistrationDto registeredUser = Generator.Registration.getRegUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.login);
        $("[data-test-id='password'] input").setValue(registeredUser.password);
        $("button.button").click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorNotRegister(){
        Generator.RegistrationDto notRegisteredUser = Generator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.login);
        $("[data-test-id='password'] input").setValue(notRegisteredUser.password);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldSuccessfulRegBlockUser(){
        Generator.RegistrationDto registeredUser = Generator.Registration.getRegUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.login);
        $("[data-test-id='password'] input").setValue(registeredUser.password);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorPassword(){
        Generator.RegistrationDto RegisteredUser = Generator.Registration.getRegUser("active");
        String wrongPassword = Generator.generatePassword();
        $("[data-test-id='login'] input").setValue(RegisteredUser.login);
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }


    @Test
    void shouldErrorLogin(){
        Generator.RegistrationDto RegisteredUser = Generator.Registration.getRegUser("active");
        String wrongLogin = Generator.generateLogin();
        $("[data-test-id='login'] input").setValue(RegisteredUser.login);
        $("[data-test-id='password'] input").setValue(wrongLogin);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
