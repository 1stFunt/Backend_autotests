package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewCredProdData {

    private String creditType;
    private String description;
    private String minPeriodMonths;
    private String maxPeriodMonths;
    private String minSum;
    private String maxSum;
    private boolean loanCollateral;
    private String constantRate;
    private String currencyCode;
    private boolean earlyRepayment;
    //Дефолтный сеттер/геттер создавался с именем "active", вместо "isActive", что неверно. Создал вручную.
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isActive;
    private String pictureName;
    private boolean loanGuarantors;
    private boolean creditInsurance;
    private String creationDate;

    private void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public NewCredProdData(String creditType, String description, String minPeriodMonths,
                           String maxPeriodMonths, String minSum, String maxSum,
                           boolean loanCollateral, String constantRate, String currencyCode,
                           boolean earlyRepayment, boolean isActive, String pictureName,
                           boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        this.setCreditType(creditType);
        this.setDescription(description);
        this.setMinPeriodMonths(minPeriodMonths);
        this.setMaxPeriodMonths(maxPeriodMonths);
        this.setMinSum(minSum);
        this.setMaxSum(maxSum);
        this.setLoanCollateral(loanCollateral);
        this.setConstantRate(constantRate);
        this.setCurrencyCode(currencyCode);
        this.setEarlyRepayment(earlyRepayment);
        this.setIsActive(isActive);
        this.setPictureName(pictureName);
        this.setLoanGuarantors(loanGuarantors);
        this.setCreditInsurance(creditInsurance);
        this.setCreationDate(creationDate);
    }

    public NewCredProdData(String creditType, String description, String minPeriodMonths,
                           String maxPeriodMonths, String minSum, String maxSum,
                           boolean loanCollateral, String constantRate,
                           boolean earlyRepayment, boolean isActive, String pictureName,
                           boolean loanGuarantors, boolean creditInsurance, String creationDate) {
        this.setCreditType(creditType);
        this.setDescription(description);
        this.setMinPeriodMonths(minPeriodMonths);
        this.setMaxPeriodMonths(maxPeriodMonths);
        this.setMinSum(minSum);
        this.setMaxSum(maxSum);
        this.setLoanCollateral(loanCollateral);
        this.setConstantRate(constantRate);
        this.setEarlyRepayment(earlyRepayment);
        this.setIsActive(isActive);
        this.setPictureName(pictureName);
        this.setLoanGuarantors(loanGuarantors);
        this.setCreditInsurance(creditInsurance);
        this.setCreationDate(creationDate);
    }
}