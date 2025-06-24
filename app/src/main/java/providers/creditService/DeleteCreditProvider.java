package providers.creditService;

import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import providers.ApiProvider;
import test_data.DeleteCreditTestCases;

import static endpoints.MainPath.*;
import static endpoints.creditService.DeleteCreditEndpoint.*;

@Getter
public class DeleteCreditProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + ENDPOINT + "/";

    public ValidatableResponse patchDeleteCreditRequest(String url, boolean isActive, boolean inArchive) {
        return patchRequest(url, DeleteCreditTestCases.accessToken(),
                            DeleteCreditTestCases.testData(isActive, inArchive));
    }

    public ValidatableResponse patchDeleteCreditRequest(String url, boolean isActive) {
        return patchRequest(url, DeleteCreditTestCases.accessToken(),
                            DeleteCreditTestCases.testData(isActive));
    }

    public ValidatableResponse patchNegativeInvTokenDeleteCreditRequest(String url, boolean isActive, boolean inArchive) {
        return patchRequest(url, DeleteCreditTestCases.invAccessToken(),
                            DeleteCreditTestCases.testData(isActive, inArchive));
    }

    public ValidatableResponse patchNegativeNoAuthDeleteCreditRequest(String url, boolean isActive, boolean inArchive) {
        return patchRequestNoAuth(url, DeleteCreditTestCases.testData(isActive, inArchive));
    }

    public ValidatableResponse patchNegativeInvIdDeleteCreditRequest(String url, boolean isActive, boolean inArchive) {
        return patchRequest(url + "defect", DeleteCreditTestCases.accessToken(),
                            DeleteCreditTestCases.testData(isActive, inArchive));
    }
}