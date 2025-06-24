package test_data.depositService;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.depositService.DepositConditionsFetcherConstans.TOKEN_PATH;

@UtilityClass
public class DepositConditionsFetcherTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTokTestData() {
        AuthProvider AuthProviderDepositConditionsFetcherCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderDepositConditionsFetcherCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderDepositConditionsFetcherCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderDepositConditionsFetcherCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}