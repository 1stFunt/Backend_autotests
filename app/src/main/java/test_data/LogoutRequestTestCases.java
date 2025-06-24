package test_data;

import data.LogoutData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;
import static data.constants.authenticationService.LogoutConstants.*;

@UtilityClass
public class LogoutRequestTestCases {

    //Актуальный токен
    public LogoutData positiveTestData() {
        AuthProvider AuthProviderLogoutCase = new AuthProvider();
        return new LogoutData()
                .setRefreshToken(ResponseUtils.getResponseValToString(
                                 AuthProviderLogoutCase.postPositiveRequestAuth(), TOKEN_PATH));
    }
     public LogoutData negativeIncTokenData() {
        AuthProvider AuthProviderLogoutCase = new AuthProvider();
        return new LogoutData()
                .setRefreshToken(ResponseUtils.getResponseValToString(
                                 AuthProviderLogoutCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect");
     }
}