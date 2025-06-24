package providers.authenticationService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.RefValRequestTestCases;
import test_data.TokValRequestTestCases;

import static endpoints.authenticationService.TokValEndpoint.*;
import static endpoints.MainPath.*;

public class TokValProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestTokVal() {
        return postRequest(URL, TokValRequestTestCases.positiveTestData());
    }

    public ValidatableResponse postNegativeInvTokRequestTokVal() {
        return postRequest(URL, TokValRequestTestCases.negativeInvTokTestData());
    }

    public ValidatableResponse postPositiveRequestRefValResponse() {
        return postRequest(URL, RefValRequestTestCases.positiveTestDataResponse());
    }

    public ValidatableResponse postNegativeNoTokRequestTokVal() {
        return postRequest(URL, TokValRequestTestCases.negativeNoTokTestData());
    }
}