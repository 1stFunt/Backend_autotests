package test_data;

import data.AuthData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthRequestTestCases {

    // Верные креды
    public AuthData positiveTestData() {
        return new AuthData()
                .setLogin("Example")
                .setPassword("Example");
    }

    // Несуществующий логин
    public AuthData negativeTestDataNonExLog() {
        return new AuthData()
                .setLogin("false_login")
                .setPassword("false_password");
    }

    // Неверный пароль к верному логину
    public AuthData negativeTestDataInvPas() {
        return new AuthData()
                .setLogin("Example")
                .setPassword("Example");
    }

    // Заблоченный пользак
    public AuthData negativeTestDataBlocked() {
        return new AuthData()
                .setLogin("Example1")
                .setPassword("Example1");
    }
}