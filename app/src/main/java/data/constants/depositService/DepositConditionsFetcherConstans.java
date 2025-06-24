package data.constants.depositService;

import utilities.SchemaUtils;

public class DepositConditionsFetcherConstans {

    private DepositConditionsFetcherConstans() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessDepositConditionsFetcher.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedDepositConditionsFetcher.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}