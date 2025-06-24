package test_data;

import data.TokValData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.*;

import static data.constants.authenticationService.TokValConstants.*;

@UtilityClass
public class TokValRequestTestCases {

    // Актуальный токен
    public TokValData positiveTestData() {
        AuthProvider AuthProviderTokValCase = new AuthProvider();
        return new TokValData()
                .setAccessToken(ResponseUtils.getResponseValToString(
                                AuthProviderTokValCase.postPositiveRequestAuth(), TOKEN_PATH));
    }

    // Некорректный токен
    public TokValData negativeInvTokTestData() {
        AuthProvider AuthProviderTokValCase = new AuthProvider();
        return new TokValData()
                .setAccessToken(ResponseUtils.getResponseValToString(
                                AuthProviderTokValCase.postPositiveRequestAuth(), TOKEN_PATH)
                                + "defect");
    }

    // Пустой токен
    public TokValData negativeNoTokTestData() {
        return new TokValData()
                .setAccessToken("");
    }
}