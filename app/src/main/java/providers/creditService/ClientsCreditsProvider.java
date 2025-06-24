package providers.creditService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.ClientsCreditsTestCases;
import utilities.ResponseUtils;

import static endpoints.creditService.ClientsCreditsEndpoint.*;
import static endpoints.MainPath.*;

public class ClientsCreditsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + "/" +
            ResponseUtils.getClientVal("id",0);

    public ValidatableResponse getPositiveRequestClientsCredits() {
        return getRequest(URL, ClientsCreditsTestCases.positiveTestData());
    }

    public ValidatableResponse getNegativeIncTokRequestClientsCredits() {
        return getRequest(URL, ClientsCreditsTestCases.negativeIncTokTestData());
    }

    public ValidatableResponse getNegativeNoTokRequestClientsCredits() {
        return getNoAuthRequest(URL);
    }

    public ValidatableResponse getNegativeIncIdRequestClientsCredits() {
        return getRequest(URL + "-defect", ClientsCreditsTestCases.positiveTestData());
    }
}