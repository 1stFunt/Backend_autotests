package data.constants.authenticationService;

import utilities.SchemaUtils;

public class LogoutConstants {

    private LogoutConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessLogout.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedLogout.json");

    public static final String PATH = "Message";
    public static final String ERROR_PATH = "errorMessage";
    public static final String TOKEN_PATH = "refreshToken";
    public static final String SUCCESS_LOGOUT_MESSAGE = "refresh token удален";
}