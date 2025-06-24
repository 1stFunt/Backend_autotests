package mappers;

import data.TermDepositProductData;

import java.sql.ResultSet;
import java.sql.SQLException;

// Практичнее использовать интерфейс RowMapper из Spring JDBC вкупе с методом JdbcTemplate
public class TermDepositProductMapper {

    public static TermDepositProductData mapResultSetToTermDepositProductData(ResultSet resultSet) throws SQLException {
        return TermDepositProductData.builder()
                .name(resultSet.getString("name"))
                .startDate(resultSet.getString("start_date"))
                .endDate(resultSet.getString("end_date"))
                .currencyId(resultSet.getString("currency_id"))
                .interestRate(resultSet.getDouble("interest_rate"))
                .earlyTerminationRate(resultSet.getDouble("early_termination_rate"))
                .amountMin(resultSet.getDouble("amount_min"))
                .amountMax(resultSet.getDouble("amount_max"))
                .durationMin(resultSet.getInt("duration_min"))
                .durationMax(resultSet.getInt("duration_max"))
                .withdrawalId(resultSet.getString("withdrawal_id"))
                .replenishmentId(resultSet.getString("replenishment_id"))
                .capitalizationId(resultSet.getString("capitalization_id"))
                .info(resultSet.getString("info"))
                .build();
    }
}