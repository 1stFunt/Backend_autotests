package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegClientData {

    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String mobilePhone;
    private String birthDate;
    private String securityAnswer;
    private Passport passport;
    private Address address;

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {

        private String country;
        private String region;
        private String city;
        private String street;
        private String houseBuildNumber;
        private String buildingNumber;
        private String apartmentNumber;
        private String postcode;
        private String addressType;

        public Address(String country, String region, String city,
                       String street, String houseBuildNumber, String buildingNumber,
                       String apartmentNumber, String postcode, String addressType) {
            this.setCountry(country);
            this.setRegion(region);
            this.setCity(city);
            this.setStreet(street);
            this.setHouseBuildNumber(houseBuildNumber);
            this.setBuildingNumber(buildingNumber);
            this.setApartmentNumber(apartmentNumber);
            this.setPostcode(postcode);
            this.setAddressType(addressType);
        }

        public Address(String country, String region, String city,
                       String street, String buildingNumber, String addressType) {
            this.setCountry(country);
            this.setRegion(region);
            this.setCity(city);
            this.setStreet(street);
            this.setBuildingNumber(buildingNumber);
            this.setAddressType(addressType);
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Passport {

        private String passportSeries;
        private String passportNumber;
        private String issuanceDate;
        private String issuedBy;
        private String departmentCode;
        private String expiryDate;

        public Passport(String passportSeries, String passportNumber, String issuanceDate,
                        String issuedBy, String departmentCode, String expiryDate) {
            this.setPassportSeries(passportSeries);
            this.setPassportNumber(passportNumber);
            this.setIssuanceDate(issuanceDate);
            this.setIssuedBy(issuedBy);
            this.setDepartmentCode(departmentCode);
            this.setExpiryDate(expiryDate);
        }

        public Passport(String passportSeries, String passportNumber, String issuanceDate,
                        String departmentCode, String expiryDate) {
            this.setPassportSeries(passportSeries);
            this.setPassportNumber(passportNumber);
            this.setIssuanceDate(issuanceDate);
            this.setDepartmentCode(departmentCode);
            this.setExpiryDate(expiryDate);
        }
    }

    public RegClientData(String lastName, String firstName, String middleName,
                         String email, String mobilePhone, String birthDate,
                         String securityAnswer, Address address, Passport passport) {
        this.setAddress(address);
        this.setPassport(passport);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setEmail(email);
        this.setMobilePhone(mobilePhone);
        this.setBirthDate(birthDate);
        this.setSecurityAnswer(securityAnswer);
    }

    public RegClientData(String lastName, String firstName, String email,
                         String mobilePhone, String birthDate, String securityAnswer,
                         Address address, Passport passport) {
        this.setAddress(address);
        this.setPassport(passport);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setEmail(email);
        this.setMobilePhone(mobilePhone);
        this.setBirthDate(birthDate);
        this.setSecurityAnswer(securityAnswer);
    }
}