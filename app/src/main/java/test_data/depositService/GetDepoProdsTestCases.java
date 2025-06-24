package test_data.depositService;

import data.GetDepoProdsData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import static data.constants.depositService.GetDepoProdsConstants.TOKEN_PATH;

@UtilityClass
public class GetDepoProdsTestCases {

    // Запрос с актуальным access-токеном
    public String positiveTokTestData() {
        AuthProvider AuthProviderGetDepoProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetDepoProdsCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String negativeIncTokTestData() {
        AuthProvider AuthProviderGetDepoProdsCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderGetDepoProdsCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос с обязательными полями
    public GetDepoProdsData positiveTestData(String type, String category) {
        GetDepoProdsData data = new GetDepoProdsData()
                .setType(type)
                .setCategory(category);
        return data;
    }

    // Фильтр по полям
    public GetDepoProdsData positiveTestData(String type, String category, String field, String order, Integer limit) {
        GetDepoProdsData data = new GetDepoProdsData()
                .setType(type)
                .setCategory(category)
                .setField(field)
                .setOrder(order)
                .setLimit(limit);
        return data;
    }

    // Запрос без обязательных полей
    public GetDepoProdsData negativeTestData() {
        GetDepoProdsData data = new GetDepoProdsData();
        return data;
    }
}