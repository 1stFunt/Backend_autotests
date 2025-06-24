package creditService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.kafka.clients.consumer.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utilities.AwaitUtils;
import utilities.KafkaUtils;
import utilities.ResponseUtils;
import java.util.regex.Pattern;

import static data.constants.creditService.ModCreditConstants.*;
import static data.constants.ProdCommonValues.*;
import static data.constants.StatusCode.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EP-5 Редактирование кредитных продуктов банка")
public class ModCreditTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Credit Service";
    private final String STORY = "EP-5 Редактирование кредитных продуктов банка";

    private static final KafkaConsumer<String, String> consumer =
                         KafkaUtils.createKafkaConsumer(KafkaUtils.newNewKafkaProps(KAFKA_URL));

    @BeforeAll
    static void subscribeThisConsumerToTopic() {
        Pattern topic = Pattern.compile("credit_product_updates");
        KafkaUtils.subscribeConsumerToTopics(consumer, topic);
        KafkaUtils.getTopicsRecords(consumer, 12000);
    }

    @AfterAll
    static void closeTestResources() {
        KafkaUtils.closeKafkaConsumer(consumer);
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Позитивный тест")
    @CsvSource(value = {TEST_CREDIT_ID + ", CAR, Test credit to modify, 1, 30, 100.0, 1000000.0, " +
                        "false, 30.0, RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        TEST_CREDIT_ID + ", PERSONAL, Test credit to modify, 3, 23, 1000.0, 2357000.0, " +
                        "false, 70.5, USD, false, false, some_picture, false, false, 2020-10-07",
                        // div
                        TEST_CREDIT_ID + ", CONSOLIDATION, Test credit to modify, 12, 75, 10000.0, 3000000.0, " +
                        "false, 52.3, EUR, false, false, picName3, false, false, 2022-12-01"})
    void posTest(String productId, String creditType, String description,
                 String minPeriodMonths, String maxPeriodMonths, String minSum,
                 String maxSum, boolean loanCollateral, String constantRate,
                 String currencyCode, boolean earlyRepayment, boolean isActive,
                 String pictureName, boolean loanGuarantors, boolean creditInsurance,
                 String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putModCreditRequest(productId, creditType, description,
                                                                               minPeriodMonths, maxPeriodMonths, minSum,
                                                                               maxSum, loanCollateral, constantRate,
                                                                               currencyCode, earlyRepayment, isActive,
                                                                               pictureName, loanGuarantors, creditInsurance,
                                                                               creationDate);
        AwaitUtils.awaitedCheckResponseStatusCode(response, SUCCESS_STATUS_CODE, 12);
        assertTrue(ResponseUtils.checkCredProd(productId, constantRate));
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertTrue(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(Невалидные данные в поле creditType)")
    @CsvSource(value = {TEST_CREDIT_ID + ", CARS, Test credit to modify, 1, 30, 100.0, 1000000.0, " +
                        "false, 30.0, RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        TEST_CREDIT_ID + ", C_AR, Test credit to modify, 3, 23, 1000.0, 2357000.0, " +
                        "false, 70.5, USD, false, false, some_picture, false, false, 2020-10-07",
                        // div
                        TEST_CREDIT_ID + ", CAR1, Test credit to modify, 12, 75, 10000.0, 3000000.0, " +
                        "false, 52.3, EUR, false, false, picName3, false, false, 2022-12-01",
                        // div
                        TEST_CREDIT_ID + ", /CAR, Test credit to modify, 12, 75.8, 10000.0, 3000000.0, " +
                        "false, 52.3, EUR, false, false, picName3, false, false, 2022-12-01",
                        // div
                        TEST_CREDIT_ID + ", CAR_, Test credit to modify, 12, 70.5, 10000.0, 3000000.0, " +
                        "false, 52.3, EUR, false, false, picName3, false, false, 2022-12-01"})
    void negInvValTest(String productId, String creditType, String description,
                       String minPeriodMonths, String maxPeriodMonths, String minSum,
                       String maxSum, boolean loanCollateral, String constantRate,
                       String currencyCode, boolean earlyRepayment, boolean isActive,
                       String pictureName, boolean loanGuarantors, boolean creditInsurance,
                       String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putModCreditRequest(productId, creditType, description,
                                                                               minPeriodMonths, maxPeriodMonths, minSum,
                                                                               maxSum, loanCollateral, constantRate,
                                                                               currencyCode, earlyRepayment, isActive,
                                                                               pictureName, loanGuarantors, creditInsurance,
                                                                               creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(не все поля)")
    @CsvSource(value = {TEST_CREDIT_ID + ", AQA_car_credit_negative, 1, 30.0000, 111.0, 1111111.0, " +
                        "false, 50.0, false, picture, false, false",
                        // div
                        TEST_CREDIT_ID + ", AQA_personal_credit_negative, 3, 40.0000, 1111.0, 2357111.0, " +
                        "false, 50.0, false, some_picture, false, false",
                        // div
                        TEST_CREDIT_ID + ", AQA_consolidation_credit_negative, 12, 70.0000, 11111.0, 3111112.0, " +
                        "false, 50.0, false, picName3, false, false"})
    void negNotAllFieldsTest(String productId, String description, String minPeriodMonths,
                             String maxPeriodMonths, String minSum, String maxSum,
                             boolean loanCollateral, String constantRate, boolean earlyRepayment,
                             String pictureName, boolean loanGuarantors, boolean creditInsurance) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putModCreditRequest(productId, description, minPeriodMonths,
                                                                               maxPeriodMonths, minSum, maxSum,
                                                                               loanCollateral, constantRate, earlyRepayment,
                                                                               pictureName, loanGuarantors, creditInsurance);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId,constantRate));
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(дефектный id)")
    @CsvSource(value = {TEST_CREDIT_ID + "defect, CAR, Test credit to modify, 1, 30, 100.0, 1000000.0, " +
                        "false, 30.0, RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        TEST_CREDIT_ID + "_, PERSONAL, Test credit to modify, 3, 23, 1000.0, 2357000.0, " +
                        "false, 70.5, USD, false, false, some_picture, false, false, 2020-10-07",
                        // div
                        TEST_CREDIT_ID + "777, CONSOLIDATION, Test credit to modify, 12, 75, 10000.0, 3000000.0, " +
                        "false, 52.3, EUR, false, false, picName3, false, false, 2022-12-01"})
    void negInvIdTest(String productId, String creditType, String description,
                      String minPeriodMonths, String maxPeriodMonths, String minSum,
                      String maxSum, boolean loanCollateral, String constantRate,
                      String currencyCode, boolean earlyRepayment, boolean isActive,
                      String pictureName, boolean loanGuarantors, boolean creditInsurance,
                      String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putModCreditRequest(productId, creditType, description,
                                                                               minPeriodMonths, maxPeriodMonths, minSum,
                                                                               maxSum, loanCollateral, constantRate,
                                                                               currencyCode, earlyRepayment, isActive,
                                                                               pictureName, loanGuarantors, creditInsurance,
                                                                               creationDate);
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(неавторизованный запрос)")
    @CsvSource(value = {TEST_CREDIT_ID + ", WEDDING, AQA_wedding_credit_negative, 1, 30.0000, 123.0, 1111111.0, false, 75.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negNoAuthTestString(String productId, String creditType, String description,
                             String minPeriodMonths, String maxPeriodMonths, String minSum,
                             String maxSum, boolean loanCollateral, String constantRate,
                             String currencyCode, boolean earlyRepayment, boolean isActive,
                             String pictureName, boolean loanGuarantors, boolean creditInsurance,
                             String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putNegativeNoAuthModCreditRequest(productId, creditType, description,
                                                                                             minPeriodMonths, maxPeriodMonths, minSum,
                                                                                             maxSum, loanCollateral, constantRate,
                                                                                             currencyCode, earlyRepayment, isActive,
                                                                                             pictureName, loanGuarantors, creditInsurance,
                                                                                             creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA, 12);
        AwaitUtils.awaitedCheckResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(дефектный токен доступа)")
    @CsvSource(value = {TEST_CREDIT_ID + ", WEDDING, AQA_wedding_credit_negative, 1, 30.0000, 123.0, 1111111.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negInvTokenTest(String productId, String creditType, String description,
                         String minPeriodMonths, String maxPeriodMonths, String minSum,
                         String maxSum, boolean loanCollateral, String constantRate,
                         String currencyCode, boolean earlyRepayment, boolean isActive,
                         String pictureName, boolean loanGuarantors, boolean creditInsurance,
                         String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putNegativeInvTokenModCreditRequest(productId, creditType, description,
                                                                                               minPeriodMonths, maxPeriodMonths, minSum,
                                                                                               maxSum, loanCollateral, constantRate,
                                                                                               currencyCode, earlyRepayment, isActive,
                                                                                               pictureName, loanGuarantors, creditInsurance,
                                                                                               creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA, 12);
        AwaitUtils.awaitedCheckResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(дефектный id в url)")
    @CsvSource(value = {TEST_CREDIT_ID + ", WEDDING, AQA_wedding_credit_negative, 1, 30.0000, 123.0, 1111111.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negInvUrlIdTest(String productId, String creditType, String description,
                         String minPeriodMonths, String maxPeriodMonths, String minSum,
                         String maxSum, boolean loanCollateral, String constantRate,
                         String currencyCode, boolean earlyRepayment, boolean isActive,
                         String pictureName, boolean loanGuarantors, boolean creditInsurance,
                         String creationDate) {
        ValidatableResponse response = MOD_CREDIT_PROVIDER.putNegativeInvIdModCreditRequest(productId, creditType, description,
                                                                                            minPeriodMonths, maxPeriodMonths, minSum,
                                                                                            maxSum, loanCollateral, constantRate,
                                                                                            currencyCode, earlyRepayment, isActive,
                                                                                            pictureName, loanGuarantors, creditInsurance,
                                                                                            creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, productId, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCodeAndSchema(response, BAD_REQUEST_STATUS_CODE, FAILED_SCHEMA, 12);
        assertFalse(ResponseUtils.checkCredProd(productId, constantRate));
    }
}