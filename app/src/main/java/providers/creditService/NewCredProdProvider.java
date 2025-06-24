package providers.creditService;

import data.NewCredProdData;
import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.NewCredProdTestCases;

import static endpoints.MainPath.*;
import static endpoints.creditService.NewCredProdEndpoint.*;

public class NewCredProdProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + ENDPOINT;

    public ValidatableResponse postNewCredProdRequest(String creditType, String description, String minPeriodMonths,
                                                      String maxPeriodMonths, String minSum, String maxSum,
                                                      boolean loanCollateral, String constantRate, String currencyCode,
                                                      boolean earlyRepayment, boolean isActive, String pictureName,
                                                      boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return postWithAuthHeadRequest(URL, NewCredProdTestCases.accessToken(),
                                       NewCredProdTestCases.testData(creditType, description, minPeriodMonths,
                                                                     maxPeriodMonths, minSum, maxSum,
                                                                     loanCollateral, constantRate, currencyCode,
                                                                     earlyRepayment, isActive, pictureName,
                                                                     loanGuarantors, creditInsurance, creationDate));
    }

    public ValidatableResponse postNewCredProdRequest(String creditType, String description, String minPeriodMonths,
                                                      String maxPeriodMonths, String minSum, String maxSum,
                                                      boolean loanCollateral, String constantRate,
                                                      boolean earlyRepayment, boolean isActive, String pictureName,
                                                      boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return postWithAuthHeadRequest(URL, NewCredProdTestCases.accessToken(),
                                       NewCredProdTestCases.testData(creditType, description, minPeriodMonths,
                                                                     maxPeriodMonths, minSum, maxSum,
                                                                     loanCollateral, constantRate,
                                                                     earlyRepayment, isActive, pictureName,
                                                                     loanGuarantors, creditInsurance, creationDate));
    }

    public ValidatableResponse postNegativeNoAuthNewCredProdRequest(String creditType, String description, String minPeriodMonths,
                                                                    String maxPeriodMonths, String minSum, String maxSum,
                                                                    boolean loanCollateral, String constantRate, String currencyCode,
                                                                    boolean earlyRepayment, boolean isActive, String pictureName,
                                                                    boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return postRequest(URL, NewCredProdTestCases.testData(creditType, description, minPeriodMonths,
                                                              maxPeriodMonths, minSum, maxSum,
                                                              loanCollateral, constantRate, currencyCode,
                                                              earlyRepayment, isActive, pictureName,
                                                              loanGuarantors, creditInsurance, creationDate));
    }

    public ValidatableResponse postNegativeInvTokenNewCredProdRequest(String creditType, String description, String minPeriodMonths,
                                                                      String maxPeriodMonths, String minSum, String maxSum,
                                                                      boolean loanCollateral, String constantRate, String currencyCode,
                                                                      boolean earlyRepayment, boolean isActive, String pictureName,
                                                                      boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        return postWithAuthHeadRequest(URL, NewCredProdTestCases.invAccessToken(),
                                       NewCredProdTestCases.testData(creditType, description, minPeriodMonths,
                                                                     maxPeriodMonths, minSum, maxSum,
                                                                     loanCollateral, constantRate, currencyCode,
                                                                     earlyRepayment, isActive, pictureName,
                                                                     loanGuarantors, creditInsurance, creationDate));
    }
}