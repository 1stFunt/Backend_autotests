package test_data;

import data.SavingsDepoProdsData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.depositService.SavingsDepoProdsConstants.TOKEN_PATH;

@UtilityClass
public class SavingsDepoProdsTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTokTestData() {
        AuthProvider AuthProviderGetDepoProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetDepoProdsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetDepoProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetDepoProdsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос с обязательными полями
    public SavingsDepoProdsData positiveTestData(String name, String date, String endDate, String currencyId,
                                                 Number interestRate, Number amountMin) {
        return new SavingsDepoProdsData()
                .setName(name)
                .setStartDate(date)
                .setEndDate(endDate)
                .setCurrencyId(currencyId)
                .setInterestRate(interestRate)
                .setAmountMin(amountMin);
    }

    // Запрос со всеми полями
    public SavingsDepoProdsData positiveTestData(String name, String date, String endDate, String currencyId,
                                                 Number interestRate, Number amountMin, Number amountMax, String info) {
        return new SavingsDepoProdsData()
                .setName(name)
                .setStartDate(date)
                .setEndDate(endDate)
                .setCurrencyId(currencyId)
                .setInterestRate(interestRate)
                .setAmountMin(amountMin)
                .setAmountMax(amountMax)
                .setInfo(info);
    }

    // Запрос без обязательных полей
    public SavingsDepoProdsData negativeTestData() {
        return new SavingsDepoProdsData();
    }
}