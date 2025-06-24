package test_data;

import data.RefValData;
import data.TokValData;
import data.constants.authenticationService.TokValConstants;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import providers.authenticationService.RefValProvider;
import utilities.*;

import static data.constants.authenticationService.RefValConstants.*;

@UtilityClass
public class RefValRequestTestCases {

    // Актуальный refresh-токен
    public RefValData positiveTestData() {
        AuthProvider AuthProviderRefValCase = new AuthProvider();
        return new RefValData()
                .setRefreshToken(ResponseUtils.getResponseValToString(
                                 AuthProviderRefValCase.postPositiveRequestAuth(), TOKEN_PATH));
    }

    // Ответ на запрос с актуальным refresh-токеном
    public TokValData positiveTestDataResponse() {
        RefValProvider TokValRefValCase = new RefValProvider();
        return new TokValData()
                .setAccessToken(ResponseUtils.getResponseValToString(
                                TokValRefValCase.postPositiveRequestRefVal(),
                                TokValConstants.TOKEN_PATH));
    }

    // Некорректный refresh-токен
    public RefValData negativeIncRefTestData() {
        AuthProvider AuthProviderRefValCase = new AuthProvider();
        return new RefValData()
                .setRefreshToken(ResponseUtils.getResponseValToString(
                                 AuthProviderRefValCase.postPositiveRequestAuth(), TOKEN_PATH)
                                 + "defect");
    }
}