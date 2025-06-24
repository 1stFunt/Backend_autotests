package authenticationService;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.ResponseUtils;

import static data.constants.StatusCode.*;
import static data.constants.authenticationService.TokValConstants.*;

@DisplayName("EP-2 Валидация access-токена")
class TokValTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Authentication Service";
    private final String STORY = "EP-2 Валидация access-токена";

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест")
    void posTest() {
        ValidatableResponse response = TOKVAL_PROVIDER.postPositiveRequestTokVal();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE,
                                                       SUCCESS_SCHEMA);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(некорректный access-токен)")
    void negInvTokTest() {
        ValidatableResponse response = TOKVAL_PROVIDER.postNegativeInvTokRequestTokVal();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE,
                                                       FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, ERROR_PATH, INV_TOK_MESSAGE);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(пустой access-токен)")
    void negNoTokTest() {
        ValidatableResponse response = TOKVAL_PROVIDER.postNegativeNoTokRequestTokVal();
        ResponseUtils.checkResponseStatusCode(response, FORBIDDEN_STATUS_CODE);
    }
}