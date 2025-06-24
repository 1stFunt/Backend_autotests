package data.constants.clientInfoService;

import utilities.SchemaUtils;

public class RegClientConstants {

    private RegClientConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessRegClient.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedRegClient.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String SUCCESS_PATH = "message";
    public static final String SUCCESS_MESSAGE = "Регистрация прошла успешно!";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_AUTH_MESSAGE = "Неверный логин или пароль";
    public static final String JSON_FILE_NAME = "RegClient.json";
}
