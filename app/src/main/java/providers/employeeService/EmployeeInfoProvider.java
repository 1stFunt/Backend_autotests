package providers.employeeService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.EmployeeInfoTestCases;

import static endpoints.MainPath.*;
import static endpoints.employeeService.EmployeeInfoEndpoint.*;

public class EmployeeInfoProvider extends ApiProvider {
    private final String URL = BASE_URL + PORT + API + VERSION + SERVICE + ENDPOINT;
    private final String correctToken = EmployeeInfoTestCases.positiveTokTestData();
    private final String incorrectToken = EmployeeInfoTestCases.negativeIncTokTestData();

    public ValidatableResponse sendRequestWithID(String employeeId) {
        String url = BASE_URL + PORT + API + VERSION + SERVICE + "/" + employeeId + ENDPOINT;
        return getRequest(url, correctToken);
    }

    public ValidatableResponse sendRequestWithIncorrectID(String employeeId) {
        String url = BASE_URL + PORT + API + VERSION + SERVICE + "/" + employeeId + "6" + ENDPOINT;
        return getRequest(url, correctToken);
    }

    public ValidatableResponse sendRequestWithoutID() {
        return getRequest(URL, EmployeeInfoTestCases.positiveTokTestData());
    }

    public ValidatableResponse sendRequestWithIncorrectToken(String employeeId) {
        String url = BASE_URL + PORT + API + VERSION + SERVICE + "/" + employeeId + ENDPOINT;
        return getRequest(url, incorrectToken);
    }

    public ValidatableResponse sendRequestWithoutToken(String employeeId) {
        String url = BASE_URL + PORT + API + VERSION + SERVICE + "/" + employeeId + ENDPOINT;
        return getNoAuthRequest(url);
    }
}