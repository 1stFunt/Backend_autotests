package data.constants.depositService;

import utilities.SchemaUtils;

public class GetDepoProdsConstants {

    private GetDepoProdsConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessGetDepoProds.json");
    public static final String SUCCESS_SCHEMA_RESPONCE =
            SchemaUtils.getSchema("SuccessSavingsDepoProds.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedGetDepoProds.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}