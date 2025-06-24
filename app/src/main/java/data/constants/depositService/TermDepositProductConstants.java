package data.constants.depositService;

import utilities.SchemaUtils;

public class TermDepositProductConstants {

    private TermDepositProductConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessTermDepositProduct.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedTermDepositProduct.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
    public static final String MESSAGE_PATH = "message";
    public static final String SUCCESS_MESSAGE = "Новый депозитный продукт успешно внесён в базу данных";
}
