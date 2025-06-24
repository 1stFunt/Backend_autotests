package providers.cardService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.GetCardsTestCases;

import java.util.HashMap;

import static endpoints.cardService.GetCardsEndpoint.*;
import static endpoints.MainPath.*;

public class GetCardsProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE;

    private HashMap<String, String> setParams(String isArchived, String limit, String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("isArchived",isArchived);
        params.put("limit", limit);
        params.put("page", page);
        return params;
    }

    private HashMap<String, String> setParams(String isArchived, String limit, String page,
                                              String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("isArchived",isArchived);
        params.put("limit", limit);
        params.put("page", page);
        params.put("type", type);
        return params;
    }

    private HashMap<String, String> setParams(String isArchived, String limit, String page,
                                              String type, String paymentSystem) {
        HashMap<String, String> params = new HashMap<>();
        params.put("isArchived",isArchived);
        params.put("limit", limit);
        params.put("page", page);
        params.put("cardType", type);
        params.put("paymentSystem", paymentSystem);
        return params;
    }

    public ValidatableResponse getCardsRequest(String isArchived, String limit, String page) {
        return getRequestWithParams(URL, GetCardsTestCases.positiveTestData(),
                                    setParams(isArchived, limit, page));
    }

    public ValidatableResponse getCardsRequest(String isArchived, String limit, String page,
                                               String type) {
        return getRequestWithParams(URL, GetCardsTestCases.positiveTestData(),
                                    setParams(isArchived, limit, page, type));
    }

    public ValidatableResponse getCardsRequest(String isArchived, String limit, String page,
                                               String type, String paymentSystem) {
        return getRequestWithParams(URL, GetCardsTestCases.positiveTestData(),
                                    setParams(isArchived, limit, page, type, paymentSystem));
    }

    public ValidatableResponse getCardsNegativeNoParamsRequest() {
        return getRequest(URL, GetCardsTestCases.positiveTestData());
    }

    public ValidatableResponse getCardsNegativeInvTokRequest(String isArchived, String limit, String page) {
        return getRequestWithParams(URL, GetCardsTestCases.negativeIncTokTestData(),
                                    setParams(isArchived, limit, page));
    }

    public ValidatableResponse getCardsNegativeNoAuthRequest(String isArchived, String limit, String page) {
        return getNoAuthRequestWithParams(URL, setParams(isArchived, limit, page));
    }
}