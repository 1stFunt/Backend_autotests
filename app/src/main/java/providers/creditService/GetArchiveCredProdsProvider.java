package providers.creditService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import providers.authenticationService.AuthProvider;

import static endpoints.MainPath.*;
import static endpoints.creditService.GetArchiveCredProdsEndpoint.ENDPOINT;
import static endpoints.creditService.GetArchiveCredProdsEndpoint.SERVICE;
import static test_data.GetArchiveCredProdsCases.negativeIncTokTestData;
import static test_data.GetArchiveCredProdsCases.positiveTestData;

public class GetArchiveCredProdsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse getPositiveRequestGetArchiveCredProds(AuthProvider AUTH_PROVIDER) {
        return getRequest(URL, positiveTestData(AUTH_PROVIDER));
    }

    public ValidatableResponse getNegativeIncTokRequestGetArchiveCredProds(AuthProvider AUTH_PROVIDER) {
        return getRequest(URL, negativeIncTokTestData(AUTH_PROVIDER));
    }

    public ValidatableResponse getNegativeNoTokRequestGetArchiveCredProds() {
        return getNoAuthRequest(URL);
    }
}