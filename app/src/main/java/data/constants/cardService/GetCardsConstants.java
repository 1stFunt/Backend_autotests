package data.constants.cardService;

import utilities.SchemaUtils;

public class GetCardsConstants {

    private GetCardsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessGetCards.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedGetCards.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_AUTH_MESSAGE = "Неверный логин или пароль";
}