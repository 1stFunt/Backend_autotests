package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;
import static data.constants.creditService.GetCredProdsConstants.*;

@UtilityClass
public class GetCredProdsTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderGetCredProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetCredProdsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetCredProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetCredProdsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}