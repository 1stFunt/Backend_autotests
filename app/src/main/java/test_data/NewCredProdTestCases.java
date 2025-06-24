package test_data;

import data.NewCredProdData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.creditService.NewCredProdConstants.*;

@UtilityClass
public class NewCredProdTestCases {

    // Запрос с актуальным access-токеном
    public String accessToken() {
        AuthProvider AuthProviderNewCredProdCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderNewCredProdCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с дефектным access-токеном
    public String invAccessToken() {
        AuthProvider AuthProviderNewCredProdCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderNewCredProdCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос со всеми полями
    public NewCredProdData testData(String creditType, String description, String minPeriodMonths,
                                    String maxPeriodMonths, String minSum, String maxSum,
                                    boolean loanCollateral, String constantRate, String currencyCode,
                                    boolean earlyRepayment, boolean isActive, String pictureName,
                                    boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return new NewCredProdData(creditType, description, minPeriodMonths,
                                   maxPeriodMonths, minSum, maxSum,
                                   loanCollateral, constantRate, currencyCode,
                                   earlyRepayment, isActive, pictureName,
                                   loanGuarantors, creditInsurance, creationDate);
    }

    // Запрос не со всеми полями
    public NewCredProdData testData(String creditType, String description, String minPeriodMonths,
                                    String maxPeriodMonths, String minSum, String maxSum,
                                    boolean loanCollateral, String constantRate,
                                    boolean earlyRepayment, boolean isActive, String pictureName,
                                    boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return new NewCredProdData(creditType, description, minPeriodMonths,
                                   maxPeriodMonths, minSum, maxSum,
                                   loanCollateral, constantRate,
                                   earlyRepayment, isActive, pictureName,
                                   loanGuarantors, creditInsurance, creationDate);
    }
}