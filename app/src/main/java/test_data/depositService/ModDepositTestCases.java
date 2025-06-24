package test_data.depositService;

import data.ModDepositData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.depositService.ModDepositConstants.*;

@UtilityClass
public class ModDepositTestCases {

    // Получение актуального токена
    public String positiveAccessToken() {
        AuthProvider AuthProviderModDepositCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(AuthProviderModDepositCase.postPositiveRequestAuth(),
                                                    TOKEN_PATH);
    }

    // Получение дефектного токена
    public String negativeIncAccessToken() {
        AuthProvider AuthProviderModDepositCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(AuthProviderModDepositCase.postPositiveRequestAuth(),
                TOKEN_PATH) + "defect";
    }

    // Запрос с набором имен депозита
    public ModDepositData ModTestData(String name) {
        return new ModDepositData(name);
    }

    // Запрос с наборами имен и опциями досрочного закрытия депозита
    public ModDepositData ModTestData(String name, boolean withdraw) {
        return new ModDepositData(name, withdraw);
    }

    // Запрос с наборами имен, опциями досрочного закрытия и границами вкладываемой суммы депозита
    public ModDepositData ModTestData(String name, boolean withdraw,
                                             String amountMin, String amountMax) {
        return new ModDepositData(name, withdraw, amountMin, amountMax);
    }
}