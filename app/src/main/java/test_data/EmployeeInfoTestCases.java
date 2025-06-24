package test_data;

import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.employeeService.EmployeeInfoConstants.TOKEN_PATH;

@UtilityClass
public class EmployeeInfoTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTokTestData() {
        AuthProvider AuthProviderEmployeeInfoCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderEmployeeInfoCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderEmployeeInfoCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderEmployeeInfoCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}