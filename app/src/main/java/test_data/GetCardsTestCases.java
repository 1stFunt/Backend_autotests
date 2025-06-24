package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.cardService.GetCardsConstants.*;

@UtilityClass
public class GetCardsTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderGetCardsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetCardsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetCardsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetCardsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}