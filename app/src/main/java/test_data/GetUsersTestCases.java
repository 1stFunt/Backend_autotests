package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;
import static data.constants.authenticationService.GetUsersConstants.*;

@UtilityClass
public class GetUsersTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTestData() {
        AuthProvider AuthProviderGetUsersCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetUsersCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetUsersCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetUsersCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос с пустым access-токеном
    public String negativeEmptyTokTestData() {
        return "";
    }
}