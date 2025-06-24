package providers.creditService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.GetCredProdsTestCases;

import static endpoints.creditService.GetCredProdsEndpoint.*;
import static endpoints.MainPath.*;

public class GetCredProdsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse getPositiveRequestGetCredProds() {
        return getRequest(URL, GetCredProdsTestCases.positiveTestData());
    }

    public ValidatableResponse getNegativeIncTokRequestGetCredProds() {
        return getRequest(URL, GetCredProdsTestCases.negativeIncTokTestData());
    }

    public ValidatableResponse getNegativeNoTokRequestGetCredProds() {
        return getNoAuthRequest(URL);
    }
}