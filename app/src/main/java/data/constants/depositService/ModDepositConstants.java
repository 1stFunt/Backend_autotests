package data.constants.depositService;

import utilities.SchemaUtils;

public class ModDepositConstants {

    private ModDepositConstants() {
    }

    public static final String SUCCESS_SCHEMA = SchemaUtils.getSchema("SuccessModDeposit.json");
    public static final String FAILED_SCHEMA = SchemaUtils.getSchema("FailedModDeposit.json");
    public static final String TOKEN_PATH = "accessToken";
    public static final String NAME_PATH = "name";
    public static final String ERROR_PATH = "errorMessage";
    public static final String SUCCESS_PATH = "message";
    public static final String SUCCESS_MESSAGE = "Изменения успешно сохранены";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
    public static final String INC_NAME_MESSAGE = "Неверное название депозита";
}