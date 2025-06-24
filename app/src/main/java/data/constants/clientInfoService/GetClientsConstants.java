package data.constants.clientInfoService;

import utilities.SchemaUtils;

public class GetClientsConstants {

    private GetClientsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessGetClients.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedGetClients.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_AUTH_MESSAGE = "Неверный логин или пароль";
    public static final int CLIENTS_DB_ENTRIES = 34;
    public static final int PAGE_SIZE = 25;
}