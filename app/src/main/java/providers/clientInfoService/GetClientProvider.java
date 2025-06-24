package providers.clientInfoService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.GetClientTestCases;
import utilities.ResponseUtils;

import static endpoints.clientInfoService.GetClientEndpoint.*;
import static endpoints.MainPath.*;

public class GetClientProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT +
            "/" + ResponseUtils.getClientVal("id", 3);

    private final String NO_ID_URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse getPositiveRequestGetClient() {
        return getRequest(URL, GetClientTestCases.positiveTestData());
    }

    public ValidatableResponse getNegativeIncTokRequestGetClient() {
        return getRequest(URL, GetClientTestCases.negativeIncTokTestData());
    }

    public ValidatableResponse getNegativeNoAuthRequestGetClient() {
        return getNoAuthRequest(URL);
    }

    public ValidatableResponse getNegativeBadIdRequestGetClient() {
        return getRequest(URL + "defect", GetClientTestCases.positiveTestData());
    }

    public ValidatableResponse getNegativeNoIdRequestGetClient() {
        return getRequest(NO_ID_URL, GetClientTestCases.positiveTestData());
    }
}