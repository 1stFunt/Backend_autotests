package test_data;

import data.GetClientsData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;
import static data.constants.clientInfoService.GetClientsConstants.*;

@UtilityClass
public class GetClientsTestCases {

    // Запрос с актуальным access-токеном
    public String accessToken() {
        AuthProvider AuthProviderGetClientsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetClientsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String invAccessToken() {
        AuthProvider AuthProviderGetClientsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetClientsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос без фильтрации
    public Object positiveTestData() {
        return "{}";
    }

    // Запрос с фильтром по статусу
    public Object positiveTestData(String status) {
        return "{\"status\": \"" + status + "\"}";
    }

    // Запрос с пагинацией
    public GetClientsData positiveTestData(int pageNumber) {
        return new GetClientsData(pageNumber);
    }

    // Запрос с фильтром по 4 полям
    public Object positiveTestData(String firstName, String lastName,
                                      String mobilePhone, String birthDate) {
        return "{\"firstName\": \"" + firstName + "\"," +
                "\"lastName\": \"" + lastName + "\"," +
                "\"mobilePhone\": \"" + mobilePhone + "\"," +
                "\"birthDate\": \"" + birthDate + "\"}";
    }
}