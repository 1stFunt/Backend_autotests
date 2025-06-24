package data.constants.employeeService;

import utilities.SchemaUtils;

public class EmployeeInfoConstants {

    private EmployeeInfoConstants() {
    }

    public static final String SUCCESS_SCHEMA =
            SchemaUtils.getSchema("SuccessEmployeeInfo.json");
    public static final String FAILED_SCHEMA =
            SchemaUtils.getSchema("FailedEmployeeInfo.json");

    public static final String TOKEN_PATH = "accessToken";
    public static final String ERROR_PATH = "errorMessage";
    public static final String INC_TOKEN_MESSAGE = "Подпись access-токена недействительна";
    public static final String NO_TOKEN_MESSAGE = "Неверный логин или пароль";
}