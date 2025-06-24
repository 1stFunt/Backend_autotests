package test_data.depositService;

import data.TermDepositProductData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.depositService.TermDepositProductConstants.TOKEN_PATH;

@UtilityClass
public class TermDepositProductTestCases {

    // Получение актуального access-токена
    public String positiveTokTestData() {
        AuthProvider authProvider = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                authProvider.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Получение некорректного access-токена
    public String negativeIncTokTestData() {
        return positiveTokTestData() + "defect";
    }

    // Создание пустого тела запроса для негативного теста
    public TermDepositProductData negativeTestData() {
        return TermDepositProductData.builder().build();
    }
}