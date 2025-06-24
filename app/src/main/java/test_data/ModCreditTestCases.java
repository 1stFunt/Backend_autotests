package test_data;

import data.ModCreditData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.creditService.NewCredProdConstants.*;

@UtilityClass
public class ModCreditTestCases {

    // Запрос с актуальным access-токеном
    public String accessToken() {
        AuthProvider AuthProviderModCreditCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderModCreditCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с дефектным access-токеном
    public String invAccessToken() {
        AuthProvider AuthProviderModCreditCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderModCreditCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос со всеми полями
    public ModCreditData testData(String productId, String creditType, String description,
                                  String minPeriodMonths, String maxPeriodMonths, String minSum,
                                  String maxSum, boolean loanCollateral, String constantRate,
                                  String currencyCode, boolean earlyRepayment, boolean isActive,
                                  String pictureName, boolean loanGuarantors, boolean creditInsurance,
                                  String creationDate) {
        return new ModCreditData(productId, creditType, description,
                                 minPeriodMonths, maxPeriodMonths, minSum,
                                 maxSum, loanCollateral, constantRate,
                                 currencyCode, earlyRepayment, isActive,
                                 pictureName, loanGuarantors, creditInsurance,
                                 creationDate);
    }

    // Запрос не со всеми полями
    public ModCreditData testData(String productId, String description, String minPeriodMonths,
                                  String maxPeriodMonths, String minSum, String maxSum,
                                  boolean loanCollateral, String constantRate, boolean earlyRepayment,
                                  String pictureName, boolean loanGuarantors, boolean creditInsurance) {
        return new ModCreditData(productId, description, minPeriodMonths,
                                 maxPeriodMonths, minSum, maxSum,
                                 loanCollateral, constantRate, earlyRepayment,
                                 pictureName, loanGuarantors, creditInsurance);
    }
}