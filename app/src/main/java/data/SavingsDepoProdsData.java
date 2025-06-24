package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsDepoProdsData {

    private String name;
    private String startDate;
    private String endDate;
    private String currencyId;
    private Number interestRate;
    private Number amountMin;
    private Number amountMax;
    private String info;
}