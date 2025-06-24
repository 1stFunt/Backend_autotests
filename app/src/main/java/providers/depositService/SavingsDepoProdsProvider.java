package providers.depositService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.SavingsDepoProdsTestCases;

import static endpoints.depositService.SavingsDepoProdsEndpoint.*;
import static endpoints.MainPath.*;

public class SavingsDepoProdsProvider extends ApiProvider {
    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;

    public ValidatableResponse postPositiveRequestSavingsDepoProds(String name, String date, String endDate,
                                                                   String currencyId, Number interestRate, Number amountMin) {
        return postWithAuthHeadRequest(URL, SavingsDepoProdsTestCases.positiveTokTestData(),
                SavingsDepoProdsTestCases.positiveTestData(name, date, endDate, currencyId, interestRate, amountMin));
    }

    public ValidatableResponse postPositiveRequestSavingsDepoProds(String name, String date, String endDate,
                                                                   String currencyId, Number interestRate, Number amountMin,
                                                                   Number amountMax, String info) {
        return postWithAuthHeadRequest(URL, SavingsDepoProdsTestCases.positiveTokTestData(),
                SavingsDepoProdsTestCases.positiveTestData(name, date, endDate, currencyId, interestRate, amountMin, amountMax, info));
    }

    public ValidatableResponse postNegativeRequestSavingsDepoProds() {
        return postWithAuthHeadRequest(URL, SavingsDepoProdsTestCases.positiveTokTestData(),
                SavingsDepoProdsTestCases.negativeTestData());
    }

    public ValidatableResponse postNegativeRequestSavingsDepoProds(String name, String date, String endDate,
                                                                   String currencyId, Number interestRate, Number amountMin) {
        return postWithAuthHeadRequest(URL, SavingsDepoProdsTestCases.negativeIncTokTestData(),
                SavingsDepoProdsTestCases.positiveTestData(name, date, endDate, currencyId, interestRate, amountMin));
    }

    public ValidatableResponse postNegativeNoAuthRequestDepoProds(String name, String date, String endDate,
                                                                  String currencyId, Number interestRate, Number amountMin) {
        return postRequest(URL, SavingsDepoProdsTestCases.positiveTestData(name, date, endDate, currencyId, interestRate, amountMin));
    }
}
