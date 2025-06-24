package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.checkerframework.checker.optional.qual.OptionalBottom;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModDepositData {

    private String name;
    private String type;
    private String interestRate;
    private String currencyCode;
    private String capitalization;
    private boolean withdraw;
    private String replenishment;
    private String amountMin;
    private String amountMax;
    private String duration;
    private String depositInfo;

    public ModDepositData() {
        this.setName("Выгодный");
    }

    public ModDepositData(String name) {
        this.setName(name);
    }

    public ModDepositData(String name, boolean withdraw) {
        this.setName(name);
        this.setWithdraw(withdraw);
    }

    public ModDepositData(String name, boolean withdraw,
                          String amountMin, String amountMax) {
        this.setName(name);
        this.setWithdraw(withdraw);
        this.setAmountMin(amountMin);
        this.setAmountMax(amountMax);
    }
}