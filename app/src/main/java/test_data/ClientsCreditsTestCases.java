package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;
import static data.constants.creditService.ClientsCreditsConstants.*;

@UtilityClass
public class ClientsCreditsTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderClientsCreditsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderClientsCreditsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderClientsCreditsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderClientsCreditsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}