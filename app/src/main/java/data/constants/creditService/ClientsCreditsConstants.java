package data.constants.creditService;

import utilities.SchemaUtils;

public class ClientsCreditsConstants {

    private ClientsCreditsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessClientsCredits.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedClientsCredits.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}