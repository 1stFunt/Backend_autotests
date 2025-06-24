package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetClientsData {

    private int pageNumber;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String birthDate;
    private String status;

    public GetClientsData(int pageNumber) {
        this.setPageNumber(pageNumber);
    }

    public GetClientsData(String status) {
        this.setStatus(status);
    }

    public GetClientsData(String firstName, String lastName,
                          String mobilePhone, String birthDate) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMobilePhone(mobilePhone);
        this.setBirthDate(birthDate);
    }
}