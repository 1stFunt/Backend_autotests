package data.constants.creditService;

import utilities.SchemaUtils;

public class ModCreditConstants {

    private ModCreditConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessModCredit.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedModCredit.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String MESSAGE_PATH = "message";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
    public static final String SUCCESS_MESSAGE = "Изменения в кредитном продукте успешно сохранены";
    public static final String TEST_CREDIT_ID = "6f3c2bcd-439b-47ab-be18-74c67de35efb";
}