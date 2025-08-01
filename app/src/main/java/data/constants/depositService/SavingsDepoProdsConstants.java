package data.constants.depositService;

import utilities.SchemaUtils;

public class SavingsDepoProdsConstants {

    private SavingsDepoProdsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessSavingsDepoProds.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedSavingsDepoProds.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}