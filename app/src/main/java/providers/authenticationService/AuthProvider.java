package providers.authenticationService;

import providers.ApiProvider;
import test_data.AuthRequestTestCases;
import io.restassured.response.ValidatableResponse;

import static endpoints.MainPath.*;
import static endpoints.authenticationService.AuthEndpoint.*;

public class AuthProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestAuth() {
        return postRequest(URL, AuthRequestTestCases.positiveTestData());
    }

    public ValidatableResponse postNegativeNonExLogRequestAuth() {
        return postRequest(URL, AuthRequestTestCases.negativeTestDataNonExLog());
    }

    public ValidatableResponse postNegativeInvPasRequestAuth() {
        return postRequest(URL, AuthRequestTestCases.negativeTestDataInvPas());
    }

    public ValidatableResponse postNegativeBlockedRequestAuth() {
        return postRequest(URL, AuthRequestTestCases.negativeTestDataBlocked());
    }
}