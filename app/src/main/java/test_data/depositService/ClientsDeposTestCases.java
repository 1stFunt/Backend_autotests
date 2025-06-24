package test_data.depositService;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;
import static data.constants.depositService.ClientsDeposConstants.*;

@UtilityClass
public class ClientsDeposTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderClientsDeposCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderClientsDeposCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderClientsDeposCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderClientsDeposCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}