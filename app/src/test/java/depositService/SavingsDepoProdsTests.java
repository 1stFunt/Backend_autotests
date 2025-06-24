package depositService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utilities.JDBCUtils;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.stream.Stream;

import static data.constants.depositService.GetDepoProdsConstants.ERROR_PATH;
import static data.constants.depositService.GetDepoProdsConstants.INC_TOKEN_MESSAGE;
import static data.constants.depositService.SavingsDepoProdsConstants.*;
import static data.constants.ProdCommonValues.AMON_DEPOSIT_DB_URL;
import static data.constants.ProdCommonValues.AMON_DB_LOGIN;
import static data.constants.ProdCommonValues.AMON_DB_PASSWORD;
import static data.constants.StatusCode.SUCCESS_STATUS_CODE;
import static data.constants.StatusCode.BAD_REQUEST_STATUS_CODE;
import static data.constants.StatusCode.FORBIDDEN_STATUS_CODE;

@DisplayName("EP-7 Создание депозитного продукта для накопительных счетов")
public class SavingsDepoProdsTests extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Deposit Service";
    private final String STORY = "ЕР-7 Создание депозитного продукта для накопительных счетов";
    static Connection connection;
    private static String currencyId;

    @BeforeAll
    static void setUp() {
        connection = JDBCUtils.openDBConnection(AMON_DEPOSIT_DB_URL, AMON_DB_LOGIN, AMON_DB_PASSWORD);
        currencyId = JDBCUtils.getActiveCurrencyId(connection, "RUB");
    }

    @AfterAll
    static void closeClientsDBConnection() {
        JDBCUtils.closeDBConnection(connection);
    }

    static Stream<Arguments> provideMandatoryFieldsData() {
        return Stream.of(
                Arguments.of("ТЕЕЕЕЕЕСТ", "2024-01-02", "2026-01-02", currencyId, 20.0, 10.0)
        );
    }

    static Stream<Arguments> provideFullFieldsData() {
        return Stream.of(
                Arguments.of("ТЕЕЕЕЕЕСТ", "2024-01-02", "2026-01-02", currencyId, 20.0, 10.0, 10000, "Тест прошёл")
        );
    }

    @ParameterizedTest
    @MethodSource("provideMandatoryFieldsData")
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест с обязательными полями")
    void posTestMandatoryData(String name, String startDate, String endDate, String currencyId, Number interestRate, Number amountMin) {
        ValidatableResponse response = SAVINGS_DEPO_PRODS_PROVIDER.postPositiveRequestSavingsDepoProds(name, startDate,
                endDate, currencyId, interestRate, amountMin);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE, SUCCESS_SCHEMA);
    }

    @ParameterizedTest
    @MethodSource("provideFullFieldsData")
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест со всеми полями")
    void posTestFullFieldsData(String name, String date, String endDate,
                               String currencyId, Number interestRate, Number amountMin,
                               Number amountMax, String info) {
        ValidatableResponse response = SAVINGS_DEPO_PRODS_PROVIDER.postPositiveRequestSavingsDepoProds(name, date,
                endDate, currencyId, interestRate, amountMin, amountMax, info);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE, SUCCESS_SCHEMA);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест с пустым body")
    void negTest() {
        ValidatableResponse response = SAVINGS_DEPO_PRODS_PROVIDER.postNegativeRequestSavingsDepoProds();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, BAD_REQUEST_STATUS_CODE, FAILED_SCHEMA);
    }

    @ParameterizedTest
    @MethodSource("provideMandatoryFieldsData")
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест (некорр. access-токен)")
    void negIncTokTest(String name, String startDate, String endDate, String currencyId, Number interestRate, Number amountMin) {
        ValidatableResponse response = SAVINGS_DEPO_PRODS_PROVIDER.postNegativeRequestSavingsDepoProds(name, startDate,
                endDate, currencyId, interestRate, amountMin);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, ERROR_PATH, INC_TOKEN_MESSAGE);
    }

    @ParameterizedTest
    @MethodSource("provideMandatoryFieldsData")
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест (пустой access-токен)")
    void negEmptyTokTest(String name, String startDate, String endDate, String currencyId, Number interestRate, Number amountMin) {
        ValidatableResponse response = SAVINGS_DEPO_PRODS_PROVIDER.postNegativeNoAuthRequestDepoProds(name, startDate,
                endDate, currencyId, interestRate, amountMin);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE);
    }
}