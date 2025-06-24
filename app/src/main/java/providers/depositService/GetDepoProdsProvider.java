package providers.depositService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.depositService.GetDepoProdsTestCases;

import static endpoints.depositService.GetDepoProdsEndpoint.*;
import static endpoints.MainPath.*;

public class GetDepoProdsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestGetDepoProds(String type, String category) {
        return postWithAuthHeadRequest(URL, GetDepoProdsTestCases.positiveTokTestData(),
                GetDepoProdsTestCases.positiveTestData(type, category));
    }

    public ValidatableResponse postNegativeRequestGetDepoProds() {
        return postWithAuthHeadRequest(URL, GetDepoProdsTestCases.positiveTokTestData(),
                GetDepoProdsTestCases.negativeTestData());
    }

    public ValidatableResponse postNegativeNoAuthRequestDepoProds(String type, String category) {
        return postRequest(URL, GetDepoProdsTestCases.positiveTestData(type, category));
    }

    public ValidatableResponse postNegativeRequestGetDepoProds(String type, String category) {
        return postWithAuthHeadRequest(URL, GetDepoProdsTestCases.negativeIncTokTestData(),
                GetDepoProdsTestCases.positiveTestData(type, category));
    }

    public ValidatableResponse postPositiveRequestGetDepoProds(String type, String category, String field,
                                                               String order, Integer limit) {
        return postWithAuthHeadRequest(URL, GetDepoProdsTestCases.positiveTokTestData(),
                GetDepoProdsTestCases.positiveTestData(type, category, field, order, limit));
    }
}