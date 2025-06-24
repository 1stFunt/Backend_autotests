package test_data;

import data.DeleteCreditData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.creditService.DeleteCreditConstants.*;

@UtilityClass
public class DeleteCreditTestCases {

    // Запрос с актуальным access-токеном
    public String accessToken() {
        AuthProvider AuthProviderDeleteCreditCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderDeleteCreditCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с дефектным access-токеном
    public String invAccessToken() {
        AuthProvider AuthProviderDeleteCreditCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderDeleteCreditCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос со всеми полями
    public DeleteCreditData testData(boolean isActive, boolean inArchive) {
        return new DeleteCreditData(isActive, inArchive);
    }

    // Запрос не со всеми полями
    public DeleteCreditData testData(boolean isActive) {
        return new DeleteCreditData(isActive);
    }
}