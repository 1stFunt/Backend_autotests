package data.constants.creditService;

import utilities.SchemaUtils;

public class DeleteCreditConstants {

    private DeleteCreditConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessDeleteCredit.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedDeleteCredit.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String MESSAGE_PATH = "message";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
    public static final String SUCCESS_MESSAGE = "Кредитный продукт успешно удален";
    public static final String CONSTANT_RATE = "67.8";
    public static final String CREDIT_ARCHIVED_KAFKA_HEADER_VAL =
            "aston.lab.absjavacreditservice.data.events.CreditProductArchivedEvent";
}