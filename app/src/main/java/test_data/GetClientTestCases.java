package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;
import static data.constants.creditService.ClientsCreditsConstants.*;

@UtilityClass
public class GetClientTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderGetClientCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetClientCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetClientCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetClientCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}