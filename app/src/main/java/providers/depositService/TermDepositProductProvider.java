package providers.depositService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.depositService.TermDepositProductTestCases;
import data.TermDepositProductData;

import static endpoints.depositService.TermDepositProductEndpoint.*;
import static endpoints.MainPath.*;

public class TermDepositProductProvider extends ApiProvider {
    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;
    private final String positiveToken = TermDepositProductTestCases.positiveTokTestData();
    private final String negativeToken = TermDepositProductTestCases.negativeIncTokTestData();

    public ValidatableResponse postPositiveRequest(TermDepositProductData testData) {
        return postWithAuthHeadRequest(URL, positiveToken, testData);
    }

    public ValidatableResponse postNegativeRequestNoAuth(TermDepositProductData testData) {
        return postRequest(URL, testData);
    }

    public ValidatableResponse postNegativeRequestInvalidToken(TermDepositProductData testData) {
        return postWithAuthHeadRequest(URL, negativeToken, testData);
    }

    public ValidatableResponse postNegativeRequest() {
        return postWithAuthHeadRequest(URL, positiveToken,
                TermDepositProductTestCases.negativeTestData());
    }
}