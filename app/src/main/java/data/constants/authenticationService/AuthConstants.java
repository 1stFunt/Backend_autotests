package data.constants.authenticationService;

import utilities.SchemaUtils;

public class AuthConstants {

    private AuthConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessAuth.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedAuth.json");

    public static final String PATH = "errorMessage";
    public static final String NONEX_LOGIN_MESSAGE = "Неверный логин";
    public static final String INC_PASS_MESSAGE = "Неверный пароль. Осталось 2 попытки";
    public static final String BLOCKED_USER_MESSAGE = "Пользователь заблокирован";
}