package providers.clientInfoService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.GetClientsTestCases;

import static endpoints.clientInfoService.GetClientsEndpoint.*;
import static endpoints.MainPath.*;

public class GetClientsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postRequestGetClients() {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.accessToken(),
                                       GetClientsTestCases.positiveTestData());
    }

    public ValidatableResponse postRequestGetClients(int pageNumber) {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.accessToken(),
                                       GetClientsTestCases.positiveTestData(pageNumber));
    }

    public ValidatableResponse postNegativeInvTokRequestGetClients() {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.invAccessToken(),
                          GetClientsTestCases.positiveTestData());
    }
    public ValidatableResponse postNegativeNoAuthRequestGetClients() {
        return postRequest(URL, GetClientsTestCases.positiveTestData());
    }

   public ValidatableResponse postRequestGetClients(String status) {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.accessToken(),
                                       GetClientsTestCases.positiveTestData(status));
   }

    public ValidatableResponse postRequestGetClients(String firstName, String lastName,
                                                     String mobilePhone, String birthDate) {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.accessToken(),
                GetClientsTestCases.positiveTestData(firstName, lastName, mobilePhone, birthDate));
    }

    public ValidatableResponse postNegativeBadFilterKeyRequestGetClients(String key) {
        return postWithAuthHeadRequest(URL, GetClientsTestCases.accessToken(),
                                 "{\"" + key + "\": \"value\"}");
    }
}