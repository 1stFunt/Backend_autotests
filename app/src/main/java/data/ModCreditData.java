package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModCreditData {

    private String product_id;
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

    public ModCreditData(String productId, String creditType, String description,
                         String minPeriodMonths, String maxPeriodMonths, String minSum,
                         String maxSum, boolean loanCollateral, String constantRate,
                         String currencyCode, boolean earlyRepayment, boolean isActive,
                         String pictureName, boolean loanGuarantors, boolean creditInsurance,
                         String creationDate) {
        this.setProduct_id(productId);
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

    public ModCreditData(String productId, String description, String minPeriodMonths,
                         String maxPeriodMonths, String minSum, String maxSum,
                         boolean loanCollateral, String constantRate, boolean earlyRepayment,
                         String pictureName, boolean loanGuarantors, boolean creditInsurance) {
        this.setProduct_id(productId);
        this.setDescription(description);
        this.setMinPeriodMonths(minPeriodMonths);
        this.setMaxPeriodMonths(maxPeriodMonths);
        this.setMinSum(minSum);
        this.setMaxSum(maxSum);
        this.setLoanCollateral(loanCollateral);
        this.setConstantRate(constantRate);
        this.setEarlyRepayment(earlyRepayment);
        this.setPictureName(pictureName);
        this.setLoanGuarantors(loanGuarantors);
        this.setCreditInsurance(creditInsurance);
    }
}