package data.constants.creditService;

import utilities.SchemaUtils;

public class GetCredProdsConstants {

    private GetCredProdsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessGetCredProds.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedGetCredProds.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}