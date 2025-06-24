package authenticationService;

import base.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.*;

import static data.constants.authenticationService.AuthConstants.*;
import static data.constants.StatusCode.*;

@DisplayName("EP-1 Аутентификация и авторизация пользователя")
class AuthTest extends BaseTest {

    private final String EPIC = "ABS";
    private final String FEATURE = "Authentication Service";
    private final String STORY = "ЕР-1 Аутентификация и авторизация пользователя";

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Позитивный тест")
    void posTest() {
        Allure.label("posTest", "Позитивный тест");
        ValidatableResponse response = AUTH_PROVIDER.postPositiveRequestAuth();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, SUCCESS_STATUS_CODE,
                                                       SUCCESS_SCHEMA);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(несущ. логин)")
    void negNonExLogTest() {
        ValidatableResponse response = AUTH_PROVIDER.postNegativeNonExLogRequestAuth();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE,
                                                       FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, PATH, NONEX_LOGIN_MESSAGE);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(неверный пароль)")
    void negInvPasTest() {
        ValidatableResponse response = AUTH_PROVIDER.postNegativeInvPasRequestAuth();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, FORBIDDEN_STATUS_CODE,
                                                       FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, PATH, INC_PASS_MESSAGE);
    }

    @Test
    @Epic(EPIC)
    @Feature(FEATURE)
    @Story(STORY)
    @DisplayName("Негативный тест(заблок. пользов.)")
    void negBlockedTest() {
        ValidatableResponse response = AUTH_PROVIDER.postNegativeBlockedRequestAuth();
        ResponseUtils.checkResponseStatusCodeAndSchema(response, NOT_ACCEPTABLE_STATUS_CODE,
                                                       FAILED_SCHEMA);
        ResponseUtils.checkResponseBody(response, PATH, BLOCKED_USER_MESSAGE);
    }
}