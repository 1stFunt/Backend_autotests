package depositService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utilities.ResponseUtils;

import java.util.stream.Stream;

import static data.constants.depositService.DepositConditionsFetcherConstans.*;
import static data.constants.StatusCode.*;

@DisplayName("EP-8 Получение списков доступных условий по депозитным продуктам")
public class DepositConditionsFetcherTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Deposit Service";
    private final String STORY = "ЕР-8 Получение списков доступных условий по депозитным продуктам";

    private static final String POSITIVE_TEST_1 = "Запрос с параметром productType и значением timeDeposit";
    private static final String POSITIVE_TEST_2 = "Запрос с параметром productType и значением savingsDeposit";

    private static final String NEGATIVE_TEST_1 = "Запрос с некорректным значением access-токена";
    private static final String NEGATIVE_TEST_2 = "Запрос с пустым значением access-токена";
    private static final String NEGATIVE_TEST_3 = "Запрос с некорректным значением параметра";
    private static final String NEGATIVE_TEST_4 = "Запрос без обязательного параметра";

    static Stream<Arguments> positiveTests() {
        return Stream.of(
                Arguments.arguments(POSITIVE_TEST_1, "timeDeposit"),
                Arguments.arguments(POSITIVE_TEST_2, "savingsDeposit")
        );
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest(name = "Тест №{index}: {0}")
    @MethodSource("positiveTests")
    @DisplayName("Позитивные тесты")
    void positiveTests(String testDescription, String value) {

        ValidatableResponse response = sendRequestWithQueryParameter(value);
        checkResponseStatusCode(response, SUCCESS_STATUS_CODE);
        validateResponseByJsonSchema(response, SUCCESS_SCHEMA);

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

        ValidatableResponse response = null;

        switch (testDescription) {
            case NEGATIVE_TEST_1 -> response = sendRequestWithoutToken();
            case NEGATIVE_TEST_2 -> response = sendRequestWithIncorrectToken();
            case NEGATIVE_TEST_3 -> response = sendRequestWithQueryParameter("time");
            case NEGATIVE_TEST_4 -> response = sendRequestWithoutRequiredQueryParameter();
        }

        checkResponseStatusCode(response, statusCode);
        validateResponseByJsonSchema(response, FAILED_SCHEMA);
        checkResponseBody(response, ERROR_PATH, message);

    }

    @Step("Отправка запроса с параметром productType и значением {value}")
    private ValidatableResponse sendRequestWithQueryParameter(String value) {
        return DEPOSIT_CONDITIONS_FETCHER_PROVIDER.sendRequestWithQueryParameter(value);
    }

    @Step("Отправка запроса без параметров")
    private ValidatableResponse sendRequestWithoutRequiredQueryParameter() {
        return DEPOSIT_CONDITIONS_FETCHER_PROVIDER.sendRequestWithoutRequiredQueryParameter();
    }

    @Step("Отправка запроса c некорректным значением access-токена")
    private ValidatableResponse sendRequestWithIncorrectToken() {
        return DEPOSIT_CONDITIONS_FETCHER_PROVIDER.sendRequestWithIncorrectToken();
    }

    @Step("Отправка запроса c пустым значением access-токена")
    private ValidatableResponse sendRequestWithoutToken() {
        return DEPOSIT_CONDITIONS_FETCHER_PROVIDER.sendRequestWithoutToken();
    }

    @Step("Проверка статус кода ответа")
    private void checkResponseStatusCode(ValidatableResponse response, int statusCode) {
        ResponseUtils.checkResponseStatusCode(response, statusCode);
    }

    @Step("Валидация тела ответа по JSON-схеме")
    private void validateResponseByJsonSchema(ValidatableResponse response, String schema) {
        ResponseUtils.validateResponseByJsonSchema(response, schema);
    }

    @Step("Проверка тела ответа")
    private void checkResponseBody(ValidatableResponse response, String path, String message) {
        ResponseUtils.checkResponseBody(response, path, message);
    }
}