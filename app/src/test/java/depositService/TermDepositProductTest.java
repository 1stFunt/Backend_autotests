package depositService;

import base.BaseTest;
import data.TermDepositProductData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utilities.JDBCUtils;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.Map;
import java.util.stream.Stream;

import static data.constants.ProdCommonValues.*;
import static data.constants.depositService.TermDepositProductConstants.*;
import static data.constants.StatusCode.BAD_REQUEST_STATUS_CODE;
import static data.constants.StatusCode.SUCCESS_STATUS_CODE;
import static data.constants.StatusCode.FORBIDDEN_STATUS_CODE;


@DisplayName("EP-4 Создание депозитного продукта для срочных вкладов")
public class TermDepositProductTest extends BaseTest {
    private final String EPIC = "ABS";
    private final String FEATURE = "Deposit Service";
    private final String STORY = "ЕР-4 Создание депозитного продукта для срочных вкладов";

    private static final String POSITIVE_TEST_1 = "Позитивный тест с обязательными полями";
    private static final String POSITIVE_TEST_2 = "Позитивный тест со всеми полями";

    private static final String NEGATIVE_TEST_1 = "Запрос с пустым body";
    private static final String NEGATIVE_TEST_2 = "Запрос с некорректным access-токеном";
    private static final String NEGATIVE_TEST_3 = "Запрос с пустым access-токеном";

    static private Connection connection;
    private static String currencyId;
    private static String withdrawalId;
    private static String replenishmentId;
    private static String capitalizationId;

    private static ValidatableResponse response;

    @BeforeAll
    static void setUp() {
        connection = JDBCUtils.openDBConnection(AMON_DEPOSIT_DB_URL, AMON_DB_LOGIN, AMON_DB_PASSWORD);
        Map<String, String> ids = JDBCUtils.getActiveIds(connection);
        currencyId = ids.get("currencyId");
        withdrawalId = ids.get("withdrawalId");
        replenishmentId = ids.get("replenishmentId");
        capitalizationId = ids.get("capitalizationId");
    }

    @AfterEach
    void cleanTestDBEntries() {
        JDBCUtils.deleteByNamePrefixAndCheck(connection, "Ночной");
    }

    @AfterAll
    static void closeClientsDBConnection() {
        JDBCUtils.closeDBConnection(connection);
    }

    private static TermDepositProductData fieldsData() {
        return TermDepositProductData.builder()
                .name("Ночной")
                .startDate("2024-01-01")
                .endDate("2026-01-01")
                .currencyId(currencyId)
                .interestRate(10.0)
                .earlyTerminationRate(0.01)
                .amountMin(1000.0)
                .durationMin(1)
                .durationMax(12)
                .withdrawalId(withdrawalId)
                .replenishmentId(replenishmentId)
                .capitalizationId(capitalizationId)
                .build(); // В большинстве случаев предпочтительней использовать Builder, а не Consumer
    }

    static Stream<Arguments> positiveTests() {
        return Stream.of(
                Arguments.arguments(POSITIVE_TEST_1, fieldsData().toBuilder().name("Ночной 1").build()),
                Arguments.arguments(POSITIVE_TEST_2, fieldsData().toBuilder()
                        .name("Ночной 2")
                        .amountMax(2000.0)
                        .info("Информация")
                        .build())
        );
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest(name = "Тест №{index}: {0}")
    @MethodSource("positiveTests")
    @DisplayName("Позитивные тесты")
    void positiveTests(String testDescription, TermDepositProductData testData) {
        response = sendPositiveRequestWith(testData);

        checkResponseStatusCode(SUCCESS_STATUS_CODE);
        validateResponseByJsonSchema(SUCCESS_SCHEMA);
        checkResponseBody(MESSAGE_PATH, SUCCESS_MESSAGE);

        TermDepositProductData dbData = JDBCUtils.getProductByName(connection, testData.getName());
        Assertions.assertAll(
                () -> Assertions.assertEquals(testData.getName(), dbData.getName(), "name"),
                () -> Assertions.assertEquals(testData.getStartDate(), dbData.getStartDate(), "start_date"),
                () -> Assertions.assertEquals(testData.getEndDate(), dbData.getEndDate(), "end_date"),
                () -> Assertions.assertEquals(testData.getCurrencyId(), dbData.getCurrencyId(), "currency_id"),
                () -> Assertions.assertEquals(testData.getInterestRate(), dbData.getInterestRate(), "interest_rate"),
                () -> Assertions.assertEquals(testData.getEarlyTerminationRate(), dbData.getEarlyTerminationRate(), "early_termination_rate"),
                () -> Assertions.assertEquals(testData.getAmountMin(), dbData.getAmountMin(), "amount_min"),
                () -> Assertions.assertEquals(testData.getDurationMin(), dbData.getDurationMin(), "duration_min"),
                () -> Assertions.assertEquals(testData.getDurationMax(), dbData.getDurationMax(), "duration_max"),
                () -> Assertions.assertEquals(testData.getWithdrawalId(), dbData.getWithdrawalId(), "withdrawal_id"),
                () -> Assertions.assertEquals(testData.getReplenishmentId(), dbData.getReplenishmentId(), "replenishment_id"),
                () -> Assertions.assertEquals(testData.getCapitalizationId(), dbData.getCapitalizationId(), "capitalization_id"),
                () -> {
                    if (testData.getInfo() != null || testData.getAmountMax() != null) {
                        Assertions.assertEquals(testData.getAmountMax(), dbData.getAmountMax(), "amount_max");
                        Assertions.assertEquals(testData.getInfo(), dbData.getInfo(), "info");
                    }
                }
        );
    }

    static Stream<Arguments> negativeTests() {
        return Stream.of(
                Arguments.arguments(NEGATIVE_TEST_1, BAD_REQUEST_STATUS_CODE, null),
                Arguments.arguments(NEGATIVE_TEST_2, FORBIDDEN_STATUS_CODE, INC_TOKEN_MESSAGE),
                Arguments.arguments(NEGATIVE_TEST_3, FORBIDDEN_STATUS_CODE, NO_TOKEN_MESSAGE));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest(name = "Тест №{index}: {0}")
    @MethodSource("negativeTests")
    @DisplayName("Негативные тесты")
    void negativeTests(String testDescription, int statusCode, String message) {

        switch (testDescription) {
            case NEGATIVE_TEST_1 -> response = sendRequestWithEmptyBody();
            case NEGATIVE_TEST_2 -> response = sendRequestWithIncorrectToken(fieldsData());
            case NEGATIVE_TEST_3 -> response = sendRequestWithoutToken(fieldsData());
        }

        checkResponseStatusCode(statusCode);
        checkResponseBody(ERROR_PATH, message);
        if (!testDescription.equals(NEGATIVE_TEST_1)) {
            validateResponseByJsonSchema(FAILED_SCHEMA);
        }
    }

    @Step("Отправка позитивного запроса на создание депозитного продукта")
    private ValidatableResponse sendPositiveRequestWith(TermDepositProductData testData) {
        return TERM_DEPOSIT_PRODUCT_PROVIDER.postPositiveRequest(testData);
    }

    @Step("Отправка запроса с пустым body")
    private ValidatableResponse sendRequestWithEmptyBody() {
        return TERM_DEPOSIT_PRODUCT_PROVIDER.postNegativeRequest();
    }

    @Step("Отправка запроса с некорректным access-токеном")
    private ValidatableResponse sendRequestWithIncorrectToken(TermDepositProductData testData) {
        return TERM_DEPOSIT_PRODUCT_PROVIDER.postNegativeRequestInvalidToken(testData);
    }

    @Step("Отправка запроса с пустым access-токеном")
    private ValidatableResponse sendRequestWithoutToken(TermDepositProductData testData) {
        return TERM_DEPOSIT_PRODUCT_PROVIDER.postNegativeRequestNoAuth(testData);
    }

    @Step("Проверка статус-кода ответа")
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