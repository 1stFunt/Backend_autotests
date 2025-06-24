package depositService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utilities.ResponseUtils;

import static data.constants.StatusCode.SUCCESS_STATUS_CODE;
import static data.constants.StatusCode.BAD_REQUEST_STATUS_CODE;
import static data.constants.StatusCode.FORBIDDEN_STATUS_CODE;
import static data.constants.depositService.GetDepoProdsConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EP-5 Просмотр депозитных продуктов банка")
public class GetDepoProdsTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Deposit Service";
    private final String STORY = "ЕР-5 Просмотр депозитных продуктов банка";

    @ParameterizedTest
    @CsvSource({
            "timeDeposit, active",
            "timeDeposit, archive",
            "timeDeposit, unpublished",
            "savingsDeposit, active",
            "savingsDeposit, archive",
            "savingsDeposit, unpublished"
    })
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест с обязательными полями")
    void posTest(String type, String category) {
        ValidatableResponse response = GET_DEPO_PRODS_PROVIDER.postPositiveRequestGetDepoProds(type, category);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE, SUCCESS_SCHEMA);
    }

    @ParameterizedTest
    @CsvSource({"savingsDeposit, unpublished, interestRate, desc, 15"})
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест (фильтр по полям)")
    void posTestFilter(String type, String category, String field, String order, Integer limit) {
        ValidatableResponse response = GET_DEPO_PRODS_PROVIDER.postPositiveRequestGetDepoProds(type, category, field,
                order, limit);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE, SUCCESS_SCHEMA);
        assertEquals(field, ResponseUtils.getResponseValToString(response, "sort.field"));
        assertEquals(order, ResponseUtils.getResponseValToString(response, "sort.order"));
        assertEquals(limit, ResponseUtils.getResponseValToInt(response, "limit"));
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест с пустым body")
    void negTest() {
        ValidatableResponse response = GET_DEPO_PRODS_PROVIDER.postNegativeRequestGetDepoProds();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, BAD_REQUEST_STATUS_CODE, FAILED_SCHEMA);
    }

    @ParameterizedTest
    @CsvSource({"timeDeposit, active"})
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест (некорр. access-токен)")
    void negIncTokTest(String type, String category) {
        ValidatableResponse response = GET_DEPO_PRODS_PROVIDER.postNegativeRequestGetDepoProds(type, category);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, ERROR_PATH, INC_TOKEN_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource({"timeDeposit, active"})
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест (пустой access-токен)")
    void negEmptyTokTest(String type, String category) {
        ValidatableResponse response = GET_DEPO_PRODS_PROVIDER.postNegativeNoAuthRequestDepoProds(type, category);
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE, FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, ERROR_PATH, NO_TOKEN_MESSAGE);
    }
}