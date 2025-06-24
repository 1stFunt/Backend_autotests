package providers.authenticationService;

import providers.ApiProvider;
import test_data.RefValRequestTestCases;
import io.restassured.response.ValidatableResponse;

import static endpoints.MainPath.*;
import static endpoints.authenticationService.RefValEndpoint.*;

public class RefValProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestRefVal() {
        return postRequest(URL, RefValRequestTestCases.positiveTestData());
    }

    public ValidatableResponse postNegativeIncRefRequestRefVal() {
        return postRequest(URL, RefValRequestTestCases.negativeIncRefTestData());
    }
}