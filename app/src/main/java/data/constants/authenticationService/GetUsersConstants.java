package data.constants.authenticationService;

import utilities.SchemaUtils;

public class GetUsersConstants {

    private GetUsersConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessGetUsers.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedGetUsers.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
}