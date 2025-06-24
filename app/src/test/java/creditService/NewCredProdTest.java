package creditService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utilities.AwaitUtils;
import utilities.JDBCUtils;
import utilities.KafkaUtils;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.regex.Pattern;

import static data.constants.creditService.NewCredProdConstants.*;
import static data.constants.ProdCommonValues.*;
import static data.constants.StatusCode.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EP-6 Добавление кредитного продукта")
public class NewCredProdTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Credit Service";
    private final String STORY = "EP-6 Добавление кредитного продукта";

    private static final KafkaConsumer<String, String> consumer =
                         KafkaUtils.createKafkaConsumer(KafkaUtils.newNewKafkaProps(KAFKA_URL));

    private static final Connection connection = JDBCUtils.openDBConnection(AMON_CREDITS_DB_URL, AMON_DB_LOGIN,
                                                                            AMON_DB_PASSWORD);

    @BeforeAll
    static void subscribeThisConsumerToTopic() {
        Pattern topic = Pattern.compile("credit_product_updates");
        KafkaUtils.subscribeConsumerToTopics(consumer, topic);
        KafkaUtils.getTopicsRecords(consumer, 12000);
    }

    @AfterAll
    static void closeTestResources() {
        KafkaUtils.closeKafkaConsumer(consumer);
        JDBCUtils.closeDBConnection(connection);
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Позитивный тест")
    @CsvSource(value = {"CAR, AQA car credit positive, 1, 30, 100.0, 1000000.0, false, 30.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        "PERSONAL, AQA personal credit positive, 3, 23, 1000.0, 2357000.0, false, 70.5, " +
                        "USD, false, false, some_picture, false, false, 2020-10-07",
                        // div
                        "CONSOLIDATION, AQA consolidation credit positive, 12, 75, 10000.0, 3000000.0, false, 52.3, " +
                        "EUR, false, false, picName3, false, false, 2022-12-01"})
    void posTest(String creditType, String description, String minPeriodMonths,
                 String maxPeriodMonths, String minSum, String maxSum,
                 boolean loanCollateral, String constantRate, String currencyCode,
                 boolean earlyRepayment, boolean isActive, String pictureName,
                 boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        JDBCUtils.cleanCreditsDBBeforeTest(connection, constantRate);
        ValidatableResponse response = NEW_CRED_PROD_PROVIDER.postNewCredProdRequest(creditType, description, minPeriodMonths,
                                                                                     maxPeriodMonths, minSum, maxSum,
                                                                                     loanCollateral, constantRate, currencyCode,
                                                                                     earlyRepayment, isActive, pictureName,
                                                                                     loanGuarantors, creditInsurance, creationDate);
        AwaitUtils.awaitedCheckResponseStatusCode(response, SUCCESS_STATUS_CODE, 12);
        assertTrue(ResponseUtils.checkCredProd(constantRate));
        assertTrue(JDBCUtils.deleteFromCreditsDBAndCheck(connection, constantRate));
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertTrue(KafkaUtils.checkCreditUpdateRecord(records, constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(Невалидные данные в поле creditType)")
    @CsvSource(value = {"car, AQA_car_credit_negative_1, 1, 30.0000, 333.0, 3333333.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        "CA_R, AQA_car_credit_negative_2, 1, 30.0000, 333.0, 3333333.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        "CAR1, AQA_car_credit_negative_3, 1, 30.0000, 333.0, 3333333.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        "/CAR, AQA_car_credit_negative_4, 1, 30.0000, 333.0, 3333333.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12",
                        // div
                        "C AR, AQA_car_credit_negative_5, 1, 30.0000, 333.0, 3333333.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negInvValTest(String creditType, String description, String minPeriodMonths,
                       String maxPeriodMonths, String minSum, String maxSum,
                       boolean loanCollateral, String constantRate, String currencyCode,
                       boolean earlyRepayment, boolean isActive, String pictureName,
                       boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        JDBCUtils.cleanCreditsDBBeforeTest(connection, constantRate);
        ValidatableResponse response = NEW_CRED_PROD_PROVIDER.postNewCredProdRequest(creditType, description, minPeriodMonths,
                                                                                     maxPeriodMonths, minSum, maxSum,
                                                                                     loanCollateral, constantRate, currencyCode,
                                                                                     earlyRepayment, isActive, pictureName,
                                                                                     loanGuarantors, creditInsurance, creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(ResponseUtils.checkCredProd(constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(не все поля)")
    @CsvSource(value = {"CAR, AQA_car_credit_negative, 1, 30.0000, 111.0, 1111111.0, false, 50.0, " +
                        "false, false, picture, false, false, 2021-01-12",
                        // div
                        "PERSONAL, AQA_personal_credit_negative, 3, 40.0000, 1111.0, 2357111.0, false, 50.0, " +
                        "false, false, some_picture, false, false, 2020-10-07",
                        // div
                        "CONSOLIDATION, AQA_consolidation_credit_negative, 12, 70.0000, 11111.0, 3111112.0, false, 50.0, " +
                        "false, false, picName3, false, false, 2022-12-01"})
    void negNotAllFieldsTest(String creditType, String description, String minPeriodMonths,
                             String maxPeriodMonths, String minSum, String maxSum,
                             boolean loanCollateral, String constantRate,
                             boolean earlyRepayment, boolean isActive, String pictureName,
                             boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        JDBCUtils.cleanCreditsDBBeforeTest(connection, constantRate);
        ValidatableResponse response = NEW_CRED_PROD_PROVIDER.postNewCredProdRequest(creditType, description, minPeriodMonths,
                                                                                     maxPeriodMonths, minSum, maxSum,
                                                                                     loanCollateral, constantRate,
                                                                                     earlyRepayment, isActive, pictureName,
                                                                                     loanGuarantors, creditInsurance, creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(ResponseUtils.checkCredProd(constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(неавторизованный запрос)")
    @CsvSource(value = {"WEDDING, AQA_wedding_credit_negative, 1, 30.0000, 123.0, 1111111.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negNoAuthTest(String creditType, String description, String minPeriodMonths,
                       String maxPeriodMonths, String minSum, String maxSum,
                       boolean loanCollateral, String constantRate, String currencyCode,
                       boolean earlyRepayment, boolean isActive, String pictureName,
                       boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        JDBCUtils.cleanCreditsDBBeforeTest(connection, constantRate);
        ValidatableResponse response = NEW_CRED_PROD_PROVIDER.postNegativeNoAuthNewCredProdRequest(creditType, description, minPeriodMonths,
                                                                                                   maxPeriodMonths, minSum, maxSum,
                                                                                                   loanCollateral, constantRate, currencyCode,
                                                                                                   earlyRepayment, isActive, pictureName,
                                                                                                   loanGuarantors, creditInsurance, creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA, 12);
        AwaitUtils.awaitedCheckResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE, 12);
        assertFalse(ResponseUtils.checkCredProd(constantRate));
    }

    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @ParameterizedTest
    @DisplayName("Негативный тест(Дефектный токен доступа)")
    @CsvSource(value = {"WEDDING, AQA_wedding_credit_negative, 1, 30.0000, 123.0, 1111111.0, false, 50.0, " +
                        "RUB, false, false, picture, false, false, 2021-01-12"})
    void negInvTokenTest(String creditType, String description, String minPeriodMonths,
                         String maxPeriodMonths, String minSum, String maxSum,
                         boolean loanCollateral, String constantRate, String currencyCode,
                         boolean earlyRepayment, boolean isActive, String pictureName,
                         boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        JDBCUtils.cleanCreditsDBBeforeTest(connection, constantRate);
        ValidatableResponse response = NEW_CRED_PROD_PROVIDER.postNegativeInvTokenNewCredProdRequest(creditType, description, minPeriodMonths,
                                                                                                     maxPeriodMonths, minSum, maxSum,
                                                                                                     loanCollateral, constantRate, currencyCode,
                                                                                                     earlyRepayment, isActive, pictureName,
                                                                                                     loanGuarantors, creditInsurance, creationDate);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        assertFalse(KafkaUtils.checkCreditUpdateRecord(records, constantRate));
        AwaitUtils.awaitedCheckResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA, 12);
        AwaitUtils.awaitedCheckResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE, 12);
        assertFalse(ResponseUtils.checkCredProd(constantRate));
    }
}