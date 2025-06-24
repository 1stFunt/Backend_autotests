package providers.authenticationService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.GetUsersTestCases;

import static endpoints.authenticationService.GetUsersEndpoint.*;
import static endpoints.MainPath.*;

public class GetUsersProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse getPositiveRequestGetUsers() {
        return getRequest(URL, GetUsersTestCases.positiveTestData());
    }

    public ValidatableResponse getNegativeIncTokRequestGetUsers() {
        return getRequest(URL, GetUsersTestCases.negativeIncTokTestData());
    }

    public ValidatableResponse getNegativeEmptyTokRequestGetUsers() {
        return getRequest(URL, GetUsersTestCases.negativeEmptyTokTestData());
    }
}