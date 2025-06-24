package data.constants.authenticationService;

import utilities.SchemaUtils;

public class RefValConstants {

    private RefValConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessRefVal.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedRefVal.json");

    public static final String TOKEN_PATH = "refreshToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOK_MESSAGE = "Подпись refresh-токена недействительна";
}
