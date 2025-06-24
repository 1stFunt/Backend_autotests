package providers.authenticationService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.LogoutRequestTestCases;

import static endpoints.MainPath.*;
import static endpoints.authenticationService.LogoutEndpoint.*;

public class LogoutProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestLogout() {
        return postRequest(URL, LogoutRequestTestCases.positiveTestData());
    }

    public ValidatableResponse postNegativeIncTokenRequestLogout() {
        return postRequest(URL, LogoutRequestTestCases.negativeIncTokenData());
    }
}