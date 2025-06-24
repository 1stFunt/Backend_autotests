package employeeService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utilities.JDBCUtils;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.stream.Stream;

import static data.constants.ProdCommonValues.*;
import static data.constants.employeeService.EmployeeInfoConstants.*;
import static data.constants.StatusCode.*;

@DisplayName("EP-1 Получение информации о конкретном сотруднике банка")
public class EmployeeInfoTests extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Employee Service";
    private final String STORY = "ЕР-1 Получение информации о конкретном сотруднике банка";

    private static final String POSITIVE_TEST_1 = "Запрос с валидным {employee_id}";

    private static final String NEGATIVE_TEST_1 = "Запрос с некорректным значением access-токена";
    private static final String NEGATIVE_TEST_2 = "Запрос с пустым значением access-токена";
    private static final String NEGATIVE_TEST_3 = "Запрос с невалидным {employee_id}";
    private static final String NEGATIVE_TEST_4 = "Запрос без {employee_id}";

    static private Connection connection;
    private static String employeeId;
    private static ValidatableResponse response;

    @BeforeAll
    static void setUp() {
        /*RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());*/
        connection = JDBCUtils.openDBConnection(AMON_EMPLOYEE_DB_URL, AMON_DB_LOGIN, AMON_DB_PASSWORD);
        employeeId = JDBCUtils.getFirstEmployeeId(connection);
    }

    @AfterAll
    static void closeClientsDBConnection() {
        JDBCUtils.closeDBConnection(connection);
    }

    static Stream<Arguments> positiveTests() {
        return Stream.of(
                Arguments.arguments(POSITIVE_TEST_1, employeeId));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest(name = "Тест №{index}: {0}")
    @MethodSource("positiveTests")
    @DisplayName("Позитивные тесты")
    void positiveTests(String testDescription, String employeeId) {
        response = sendRequestWithID(employeeId);
        checkResponseStatusCode(SUCCESS_STATUS_CODE);
        validateResponseByJsonSchema(SUCCESS_SCHEMA);
    }

    static Stream<Arguments> negativeTests() {
        return Stream.of(
                Arguments.arguments(NEGATIVE_TEST_1, FORBIDDEN_STATUS_CODE, NO_TOKEN_MESSAGE),
                Arguments.arguments(NEGATIVE_TEST_2, FORBIDDEN_STATUS_CODE, INC_TOKEN_MESSAGE),
                Arguments.arguments(NEGATIVE_TEST_3, BAD_REQUEST_STATUS_CODE, null),
                Arguments.arguments(NEGATIVE_TEST_4, BAD_REQUEST_STATUS_CODE, null)
        );
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest(name = "Тест №{index}: {0}")
    @MethodSource("negativeTests")
    @DisplayName("Негативные тесты")
    void negativeTests(String testDescription, int statusCode, String message) {
        switch (testDescription) {
            case NEGATIVE_TEST_1 -> response = sendRequestWithoutToken(employeeId);
            case NEGATIVE_TEST_2 -> response = sendRequestWithIncorrectToken(employeeId);
            case NEGATIVE_TEST_3 -> response = sendRequestWithIncorrectID(employeeId);
            case NEGATIVE_TEST_4 -> response = sendRequestWithoutID();
        }

        checkResponseStatusCode(statusCode);
        validateResponseByJsonSchema(FAILED_SCHEMA);
        checkResponseBody(ERROR_PATH, message);
    }

    @Step("Отправка запроса с валидным {employee_id}")
    private ValidatableResponse sendRequestWithID(String employeeId) {
        return EMPLOYEE_INFO_PROVIDER.sendRequestWithID(employeeId);
    }

    @Step("Отправка запроса с невалидным {employee_id}")
    private ValidatableResponse sendRequestWithIncorrectID(String employeeId) {
        return EMPLOYEE_INFO_PROVIDER.sendRequestWithIncorrectID(employeeId);
    }

    @Step("Отправка запроса без {employee_id}")
    private ValidatableResponse sendRequestWithoutID() {
        return EMPLOYEE_INFO_PROVIDER.sendRequestWithoutID();
    }

    @Step("Отправка запроса c некорректным значением access-токена")
    private ValidatableResponse sendRequestWithIncorrectToken(String employeeId) {
        return EMPLOYEE_INFO_PROVIDER.sendRequestWithIncorrectToken(employeeId);
    }

    @Step("Отправка запроса c пустым значением access-токена")
    private ValidatableResponse sendRequestWithoutToken(String employeeId) {
        return EMPLOYEE_INFO_PROVIDER.sendRequestWithoutToken(employeeId);
    }

    @Step("Проверка статус кода ответа")
    private void checkResponseStatusCode(int statusCode) {
        ResponseUtils.checkResponseStatusCode(response, statusCode);
    }

    @Step("Валидация тела ответа по JSON-схеме")
    private void validateResponseByJsonSchema(String schema) {
        ResponseUtils.validateResponseByJsonSchema(response, schema);
    }

    @Step("Проверка тела ответа")
    private void checkResponseBody(String path, String message) {
        ResponseUtils.checkResponseBody(response, path, message);
    }
}