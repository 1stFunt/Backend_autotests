package data.constants.creditService;

import utilities.SchemaUtils;

public class NewCredProdConstants {

    private NewCredProdConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessNewCredProd.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedNewCredProd.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String MESSAGE_PATH = "message";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
    public static final String SUCCESS_MESSAGE = "Кредитный продукт успешно добавлен";
}