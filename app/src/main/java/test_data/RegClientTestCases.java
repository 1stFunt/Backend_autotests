package test_data;

import data.RegClientData;
import lombok.experimental.UtilityClass;
import providers.authenticationService.AuthProvider;
import utilities.FileUtils;
import utilities.ResponseUtils;

import java.io.File;

import static data.constants.clientInfoService.RegClientConstants.*;

@UtilityClass
public class RegClientTestCases {

    // Запрос с актуальным access-токеном
    public String accessToken() {
        AuthProvider AuthProviderRegClientCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderRegClientCase.postPositiveRequestAuth(), TOKEN_PATH);
    }

    // Запрос с некорректным access-токеном
    public String invAccessToken() {
        AuthProvider AuthProviderRegClientCase = new AuthProvider();
        return ResponseUtils.getResponseValToString(
                AuthProviderRegClientCase.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }

    // Запрос со всеми полями
    public String positiveTestData(String country, String region, String city,
                                 String street, String houseBuildNumber, String buildingNumber,
                                 String apartmentNumber, String postcode, String addressType,
                                 String passportSeries, String passportNumber, String issuanceDate,
                                 String issuedBy, String departmentCode, String expiryDate,
                                 String lastName, String firstName, String middleName,
                                 String email, String mobilePhone, String birthDate,
                                 String securityAnswer) {
        RegClientData.Address address = new RegClientData.Address(country, region, city,
                                                                  street, houseBuildNumber, buildingNumber,
                                                                  apartmentNumber, postcode, addressType);
        RegClientData.Passport passport = new RegClientData.Passport(passportSeries, passportNumber, issuanceDate,
                                                                     issuedBy, departmentCode, expiryDate);
        RegClientData regClientData =  new RegClientData(lastName, firstName, middleName,
                                                         email, mobilePhone, birthDate,
                                                         securityAnswer, address, passport);
        FileUtils.writeToJsonFile(JSON_FILE_NAME, regClientData);
        File file = new File(JSON_FILE_NAME);
        return FileUtils.getJsonMapped(file);
    }

    // Запрос с обязательными полями
    public String positiveTestData(String country, String region, String city,
                                   String street, String buildingNumber, String addressType,
                                   String passportSeries, String passportNumber, String issuanceDate,
                                   String issuedBy, String departmentCode, String expiryDate,
                                   String lastName, String firstName, String email,
                                   String mobilePhone, String birthDate, String securityAnswer) {
        RegClientData.Address address = new RegClientData.Address(country, region, city,
                                                                  street, buildingNumber, addressType);
        RegClientData.Passport passport = new RegClientData.Passport(passportSeries, passportNumber, issuanceDate,
                                                                     issuedBy, departmentCode, expiryDate);
        RegClientData regClientData =  new RegClientData(lastName, firstName, email,
                                                         mobilePhone, birthDate, securityAnswer,
                                                         address, passport);
        FileUtils.writeToJsonFile(JSON_FILE_NAME, regClientData);
        File file = new File(JSON_FILE_NAME);
        return FileUtils.getJsonMapped(file);
    }

    // Запрос не со всеми обязательными полями
    public String negativeTestData(String country, String region, String city,
                                   String street, String buildingNumber, String addressType,
                                   String passportSeries, String passportNumber, String issuanceDate,
                                   String departmentCode, String expiryDate,
                                   String lastName, String firstName, String email,
                                   String mobilePhone, String birthDate, String securityAnswer) {
        RegClientData.Address address = new RegClientData.Address(country, region, city,
                                                                  street, buildingNumber, addressType);
        RegClientData.Passport passport = new RegClientData.Passport(passportSeries, passportNumber, issuanceDate,
                                                                     departmentCode, expiryDate);
        RegClientData regClientData =  new RegClientData(lastName, firstName, email,
                                                         mobilePhone, birthDate, securityAnswer,
                                                         address, passport);
        FileUtils.writeToJsonFile(JSON_FILE_NAME, regClientData);
        File file = new File(JSON_FILE_NAME);
        return FileUtils.getJsonMapped(file);
    }

    // Уродливая статическая строка
    public Object positiveUglyTestData() {
        return "{\n" +
                "    \"lastName\": \"Балбесов\",\n" +
                "    \"firstName\": \"Баклуша\",\n" +
                "    \"middleName\": \"Бывалович\",\n" +
                "    \"email\": \"thisemail@quiteuniquetoo.com\",\n" +
                "    \"mobilePhone\": \"87777774855\",\n" +
                "    \"securityAnswer\": \"баранки\",\n" +
                "    \"passport\": {\n" +
                "        \"passportSeries\": \"2222\",\n" +
                "        \"passportNumber\": \"766553\",\n" +
                "        \"issuanceDate\": \"2010-04-10\",\n" +
                "        \"issuedBy\": \"МВД по РК\",\n" +
                "        \"departmentCode\": \"100-200\",\n" +
                "        \"expiryDate\": \"07-07-2078\"\n" +
                "    },\n" +
                "    \"address\":\n" +
                "        {\n" +
                "            \"country\": \"Россия\",\n" +
                "            \"region\": \"Московская обл.\",\n" +
                "            \"city\": \"г.Балашиха\",\n" +
                "            \"street\": \"ул.Балашихинское шоссе\",\n" +
                "            \"houseBuildNumber\": \"1\",             \n" +
                "            \"buildingNumber\": \"8\",\n" +
                "            \"apartmentNumber\": \"12\",\n" +
                "            \"postcode\": \"123321\",\n" +
                "            \"addressType\": \"physical\"\n" +
                "        },\n" +
                "         \"birthDate\": \"1990-04-10\"\n" +
                "}  ";
    }
}