package providers.creditService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.ModCreditTestCases;

import static endpoints.MainPath.*;
import static endpoints.creditService.ModCreditEndpoint.*;
import static data.constants.creditService.ModCreditConstants.*;

public class ModCreditProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + ENDPOINT + "/" + TEST_CREDIT_ID;

    public ValidatableResponse putModCreditRequest(String productId, String creditType, String description,
                                                   String minPeriodMonths, String maxPeriodMonths, String minSum,
                                                   String maxSum, boolean loanCollateral, String constantRate,
                                                   String currencyCode, boolean earlyRepayment, boolean isActive,
                                                   String pictureName, boolean loanGuarantors, boolean creditInsurance,
                                                   String creationDate) {
        return putRequest(URL, ModCreditTestCases.accessToken(),
                          ModCreditTestCases.testData(productId, creditType, description,
                                                      minPeriodMonths, maxPeriodMonths, minSum,
                                                      maxSum, loanCollateral, constantRate,
                                                      currencyCode, earlyRepayment, isActive,
                                                      pictureName, loanGuarantors, creditInsurance,
                                                      creationDate));
    }

    public ValidatableResponse putModCreditRequest(String productId, String description, String minPeriodMonths,
                                                   String maxPeriodMonths, String minSum, String maxSum,
                                                   boolean loanCollateral, String constantRate, boolean earlyRepayment,
                                                   String pictureName, boolean loanGuarantors, boolean creditInsurance) {
        return putRequest(URL, ModCreditTestCases.accessToken(),
                          ModCreditTestCases.testData(productId, description,
                                                      minPeriodMonths, maxPeriodMonths, minSum,
                                                      maxSum, loanCollateral, constantRate, earlyRepayment,
                                                      pictureName, loanGuarantors, creditInsurance));
    }

    public ValidatableResponse putNegativeInvTokenModCreditRequest(String productId, String creditType, String description,
                                                                   String minPeriodMonths, String maxPeriodMonths, String minSum,
                                                                   String maxSum, boolean loanCollateral, String constantRate,
                                                                   String currencyCode, boolean earlyRepayment, boolean isActive,
                                                                   String pictureName, boolean loanGuarantors, boolean creditInsurance,
                                                                   String creationDate) {
        return putRequest(URL, ModCreditTestCases.invAccessToken(),
                          ModCreditTestCases.testData(productId, creditType, description,
                                                      minPeriodMonths, maxPeriodMonths, minSum,
                                                      maxSum, loanCollateral, constantRate,
                                                      currencyCode, earlyRepayment, isActive,
                                                      pictureName, loanGuarantors, creditInsurance,
                                                      creationDate));
    }

    public ValidatableResponse putNegativeNoAuthModCreditRequest(String productId, String creditType, String description,
                                                                 String minPeriodMonths, String maxPeriodMonths, String minSum,
                                                                 String maxSum, boolean loanCollateral, String constantRate,
                                                                 String currencyCode, boolean earlyRepayment, boolean isActive,
                                                                 String pictureName, boolean loanGuarantors, boolean creditInsurance,
                                                                 String creationDate) {
        return putRequestNoAuth(URL, ModCreditTestCases.testData(productId, creditType, description,
                                                                 minPeriodMonths, maxPeriodMonths, minSum,
                                                                 maxSum, loanCollateral, constantRate,
                                                                 currencyCode, earlyRepayment, isActive,
                                                                 pictureName, loanGuarantors, creditInsurance,
                                                                 creationDate));
    }

    public ValidatableResponse putNegativeInvIdModCreditRequest(String productId, String creditType, String description,
                                                                String minPeriodMonths, String maxPeriodMonths, String minSum,
                                                                String maxSum, boolean loanCollateral, String constantRate,
                                                                String currencyCode, boolean earlyRepayment, boolean isActive,
                                                                String pictureName, boolean loanGuarantors, boolean creditInsurance,
                                                                String creationDate) {
        return putRequest(URL + "defect", ModCreditTestCases.accessToken(),
                          ModCreditTestCases.testData(productId, creditType, description,
                                                      minPeriodMonths, maxPeriodMonths, minSum,
                                                      maxSum, loanCollateral, constantRate,
                                                      currencyCode, earlyRepayment, isActive,
                                                      pictureName, loanGuarantors, creditInsurance,
                                                      creationDate));
    }
}