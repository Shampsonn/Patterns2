import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Generator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static RegistrationDto sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        return user;
    }

    public static String generateLogin(){
        return faker.funnyName().name();
    }
    public static String generatePassword(){
        return faker.internet().password();

    }

    public static class Registration {

        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(generateLogin(), generatePassword(), status);
        }

        public static RegistrationDto getRegUser(String status) {
            return sendRequest(getUser(status));
        }
    }
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;

        public RegistrationDto(String login, String password, String status) {
            this.login = login;
            this.password = password;
            this.status = status;
        }
    }
}