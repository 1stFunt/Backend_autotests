package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder(toBuilder = true)
@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermDepositProductData {

    private String name;
    private String startDate;
    private String endDate;
    private String currencyId;
    private Double interestRate;
    private Double earlyTerminationRate;
    private Double amountMin;
    private Double amountMax;
    private Integer durationMin;
    private Integer durationMax;
    private String withdrawalId;
    private String replenishmentId;
    private String capitalizationId;
    private String info;
}