package creditService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.*;
import utilities.AwaitUtils;
import utilities.JDBCUtils;
import utilities.KafkaUtils;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.regex.Pattern;

import static data.constants.creditService.DeleteCreditConstants.*;
import static data.constants.ProdCommonValues.*;
import static data.constants.StatusCode.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("EP-4 Удаление кредитного продукта банка")
public class DeleteCreditTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Credit Service";
    private final String STORY = "EP-4 Удаление кредитного продукта банка";

    private static final KafkaConsumer<String, String> consumer =
                         KafkaUtils.createKafkaConsumer(KafkaUtils.newNewKafkaProps(KAFKA_URL));

    private static final Connection connection =
                         JDBCUtils.openDBConnection(AMON_CREDITS_DB_URL, AMON_DB_LOGIN, AMON_DB_PASSWORD);

    @BeforeAll
    static void subscribeThisConsumerToTopic() {
        Pattern topic = Pattern.compile("credit_product_updates");
        KafkaUtils.subscribeConsumerToTopics(consumer, topic);
        KafkaUtils.getTopicsRecords(consumer, 12000);
    }

    @AfterEach
    void killTestDBEntries() {
        JDBCUtils.deleteFromCreditsDBAndCheck(connection, CONSTANT_RATE);
    }

    @AfterAll
    static void closeTestResources() {
        KafkaUtils.closeKafkaConsumer(consumer);
        JDBCUtils.closeDBConnection(connection);
    }

    @Test
    @Order(1)
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест")
    void posTest() {
        String id = ResponseUtils.createAndGetCreditId();
        String url = DELETE_CREDIT_PROVIDER.getURL() + id;
        ValidatableResponse response = DELETE_CREDIT_PROVIDER.patchDeleteCreditRequest(url,false,false);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        AwaitUtils.awaitedCheckResponseStatusCode(response, SUCCESS_STATUS_CODE, 12);
        assertTrue(JDBCUtils.checkCreditIsArchived(connection, id));
        assertTrue(KafkaUtils.checkCreditUpdateRecord(records, id));
        assertTrue(KafkaUtils.checkRecordsHeaderVal(records, CREDIT_ARCHIVED_KAFKA_HEADER_VAL));
    }

    @Test
    @Order(2)
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(не все поля)")
    void negNotAllFieldsTest() {
        String id = ResponseUtils.createAndGetCreditId();
        String url = DELETE_CREDIT_PROVIDER.getURL() + id;
        ValidatableResponse response = DELETE_CREDIT_PROVIDER.patchDeleteCreditRequest(url,false);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(JDBCUtils.checkCreditIsArchived(connection, id));
        assertTrue(KafkaUtils.checkCreditUpdateRecord(records, id));
        assertFalse(KafkaUtils.checkRecordsHeaderVal(records, CREDIT_ARCHIVED_KAFKA_HEADER_VAL));
    }

    @Test
    @Order(3)
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(некорректный id)")
    void negInvIdTest() {
        String id = ResponseUtils.createAndGetCreditId();
        String url = DELETE_CREDIT_PROVIDER.getURL() + id;
        ValidatableResponse response = DELETE_CREDIT_PROVIDER
                                       .patchNegativeInvIdDeleteCreditRequest(url,false, false);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        AwaitUtils.awaitedCheckResponseStatusCode(response, BAD_REQUEST_STATUS_CODE, 12);
        assertFalse(JDBCUtils.checkCreditIsArchived(connection, id));
        assertFalse(KafkaUtils.checkRecordsHeaderVal(records, CREDIT_ARCHIVED_KAFKA_HEADER_VAL));
    }

    @Test
    @Order(4)
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(некорректный токен)")
    void negInvTokenTest() {
        String id = ResponseUtils.createAndGetCreditId();
        String url = DELETE_CREDIT_PROVIDER.getURL() + id;
        ValidatableResponse response = DELETE_CREDIT_PROVIDER
                                       .patchNegativeInvTokenDeleteCreditRequest(url,false, false);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        AwaitUtils.awaitedCheckResponseStatusCode(response, FORBIDDEN_STATUS_CODE, 12);
        assertFalse(JDBCUtils.checkCreditIsArchived(connection, id));
        assertFalse(KafkaUtils.checkRecordsHeaderVal(records, CREDIT_ARCHIVED_KAFKA_HEADER_VAL));
    }

    @Test
    @Order(5)
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(неавторизованный запрос)")
    void negNoAuthTest() {
        String id = ResponseUtils.createAndGetCreditId();
        String url = DELETE_CREDIT_PROVIDER.getURL() + id;
        ValidatableResponse response = DELETE_CREDIT_PROVIDER
                                       .patchNegativeNoAuthDeleteCreditRequest(url,false, false);
        ConsumerRecords<String, String> records = KafkaUtils.getTopicsRecords(consumer, 12000);
        AwaitUtils.awaitedCheckResponseStatusCode(response, FORBIDDEN_STATUS_CODE, 12);
        assertFalse(JDBCUtils.checkCreditIsArchived(connection, id));
        assertFalse(KafkaUtils.checkRecordsHeaderVal(records, CREDIT_ARCHIVED_KAFKA_HEADER_VAL));
    }
}