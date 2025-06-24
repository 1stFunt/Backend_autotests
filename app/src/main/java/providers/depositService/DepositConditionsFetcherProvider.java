package providers.depositService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.depositService.DepositConditionsFetcherTestCases;
import test_data.GetCardsTestCases;

import static endpoints.MainPath.*;
import static endpoints.depositService.DepositConditionsFetcherEndpoint.*;

public class DepositConditionsFetcherProvider extends ApiProvider {
    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;
    private final String parameter = "productType";
    private final String value = "timeDeposit";
    private final String correctToken = DepositConditionsFetcherTestCases.positiveTokTestData();
    private final String incorrectToken = DepositConditionsFetcherTestCases.negativeIncTokTestData();

    public ValidatableResponse sendRequestWithQueryParameter(String value) {
        return getRequestWithParam(URL, correctToken, "productType", value);
    }

    public ValidatableResponse sendRequestWithoutRequiredQueryParameter() {
        return getRequest(URL, GetCardsTestCases.positiveTestData());
    }

    public ValidatableResponse sendRequestWithIncorrectToken() {
        return getRequestWithParam(URL, incorrectToken, parameter, value);
    }

    public ValidatableResponse sendRequestWithoutToken() {
        return getNoAuthRequestWithParams(URL, parameter, value);
    }
}