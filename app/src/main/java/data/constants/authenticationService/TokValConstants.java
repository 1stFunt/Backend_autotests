package data.constants.authenticationService;

import utilities.SchemaUtils;

public class TokValConstants {

    private TokValConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessTokVal.json");

    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedTokVal.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INV_TOK_MESSAGE = "Подпись access-токена недействительна";
}