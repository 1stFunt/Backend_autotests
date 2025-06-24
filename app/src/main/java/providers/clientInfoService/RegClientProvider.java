package providers.clientInfoService;

import io.restassured.response.ValidatableResponse;
import providers.ApiProvider;
import test_data.RegClientTestCases;

import static endpoints.clientInfoService.RegClientEndpoint.*;
import static endpoints.MainPath.*;

public class RegClientProvider extends ApiProvider {

    private final String URL = BASE_URL + PORT + API + VERSION + ENDPOINT;

    public ValidatableResponse postRequestRegClient(String country, String region, String city,
                                                    String street, String houseBuildNumber, String buildingNumber,
                                                    String apartmentNumber, String postcode, String addressType,
                                                    String passportSeries, String passportNumber, String issuanceDate,
                                                    String issuedBy, String departmentCode, String expiryDate,
                                                    String lastName, String firstName, String middleName,
                                                    String email, String mobilePhone, String birthDate,
                                                    String securityAnswer) {
        return postWithAuthHeadRequest(URL, RegClientTestCases.accessToken(),
                RegClientTestCases.positiveTestData(country, region, city,
                                                    street, houseBuildNumber, buildingNumber,
                                                    apartmentNumber, postcode, addressType,
                                                    passportSeries, passportNumber, issuanceDate,
                                                    issuedBy, departmentCode, expiryDate,
                                                    lastName, firstName, middleName,
                                                    email, mobilePhone, birthDate, securityAnswer));
    }

    public ValidatableResponse postRequestRegClient(String country, String region, String city,
                                                    String street, String buildingNumber, String addressType,
                                                    String passportSeries, String passportNumber, String issuanceDate,
                                                    String issuedBy, String departmentCode, String expiryDate,
                                                    String lastName, String firstName, String email,
                                                    String mobilePhone, String birthDate, String securityAnswer) {
        return postWithAuthHeadRequest(URL, RegClientTestCases.accessToken(),
                RegClientTestCases.positiveTestData(country, region, city,
                                                    street, buildingNumber, addressType,
                                                    passportSeries, passportNumber, issuanceDate,
                                                    issuedBy, departmentCode, expiryDate,
                                                    lastName, firstName, email,
                                                    mobilePhone, birthDate, securityAnswer));
    }

    public ValidatableResponse postRequestRegClient(String country, String region, String city,
                                                    String street, String buildingNumber, String addressType,
                                                    String passportSeries, String passportNumber, String issuanceDate,
                                                    String departmentCode, String expiryDate,
                                                    String lastName, String firstName, String email,
                                                    String mobilePhone, String birthDate, String securityAnswer) {
        return postWithAuthHeadRequest(URL, RegClientTestCases.accessToken(),
                RegClientTestCases.negativeTestData(country, region, city,
                                                    street, buildingNumber, addressType,
                                                    passportSeries, passportNumber, issuanceDate,
                                                    departmentCode, expiryDate,
                                                    lastName, firstName, email,
                                                    mobilePhone, birthDate, securityAnswer));
    }

    public ValidatableResponse postRequestUglyRegClient() {
        return postWithAuthHeadRequest(URL, RegClientTestCases.accessToken(),
                                       RegClientTestCases.positiveUglyTestData());
    }

    public ValidatableResponse postNegativeInvTokRequestRegClient() {
        return postWithAuthHeadRequest(URL, RegClientTestCases.invAccessToken(),
                                       RegClientTestCases.positiveUglyTestData());
    }

    public ValidatableResponse postNegativeNoAuthRequestRegClient() {
        return postRequest(URL, RegClientTestCases.positiveUglyTestData());
    }
}