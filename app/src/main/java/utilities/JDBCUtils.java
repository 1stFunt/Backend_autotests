package utilities;

import data.TermDepositProductData;
import mappers.TermDepositProductMapper;

import java.sql.*;
import java.util.*;

public class JDBCUtils {
    static {
        LoggingUtils.setupLogger();
    }

    private JDBCUtils() {
    }

    // Практичнее использовать класс JdbcTemplate из Spring JDBC вкупе с интерфейсом RowMapper
    public static TermDepositProductData getProductByName(Connection connection, String name) {
        String query = "SELECT * FROM time_deposit_product WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Преобразуем данные из ResultSet в объект с помощью Маппера
                    return TermDepositProductMapper.mapResultSetToTermDepositProductData(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteFromCreditsDBAndCheck(Connection connection, String constantRate) {
        if (connection == null) {
            return false;
        }
        boolean checkMark = false;
        try {
            PreparedStatement statement;
            statement = connection.prepareStatement("DELETE FROM abs_credit_service_db.public.credit_product WHERE constant_rate::text = ?;");
            statement.setString(1, constantRate + "000");
            statement.executeUpdate();
            statement = connection.prepareStatement("SELECT count(*) FROM abs_credit_service_db.public.credit_product WHERE constant_rate::text = ?;");
            statement.setString(1, constantRate + "000");
            ResultSet deleteCheckSet = statement.executeQuery();
            deleteCheckSet.next();
            checkMark = deleteCheckSet.getInt(1) == 0;
        } catch (SQLException exception) {
            exception.getLocalizedMessage();
        }
        return checkMark;
    }

    public static void cleanCreditsDBBeforeTest(Connection connection, String constantRate) {
        if (connection == null) {
            return;
        }
        if (ResponseUtils.checkCredProd(constantRate)) {
            JDBCUtils.deleteFromCreditsDBAndCheck(connection, constantRate);
        }
    }

    public static boolean checkCreditIsArchived(Connection connection, String productId) {
        if (connection == null) {
            return false;
        }
        boolean checkMark = false;
        try {
            PreparedStatement statement;
            statement = connection.prepareStatement("SELECT count(*) FROM abs_credit_service_db.public.credit_product WHERE id::text = ? AND in_archive = true;");
            statement.setString(1, productId);
            ResultSet deleteCheckSet = statement.executeQuery();
            deleteCheckSet.next();
            checkMark = deleteCheckSet.getInt(1) == 1;
        } catch (SQLException exception) {
            exception.getLocalizedMessage();
        }
        return checkMark;
    }

    public static Connection openDBConnection(String url, String login, String password) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException | SQLException exception) {
            exception.getLocalizedMessage();
        }
        return connection;
    }

    public static void closeDBConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.getLocalizedMessage();
            }
        }
    }

    public static String getActiveCurrencyId(Connection connection, String currencyCode) {
        if (connection == null) {
            return null;
        }
        String query = "SELECT id FROM currency WHERE code = ? AND is_active = true LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, currencyCode); // Устанавливаем параметр ПЕРЕД вызовом executeQuery
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getObject("id").toString();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getActiveIds(Connection connection) {
        if (connection == null) {
            return Collections.emptyMap();
        }

        Map<String, String> ids = new HashMap<>();
        Map<String, String> queries = Map.of(
                "currencyId", "SELECT id FROM currency WHERE is_active = true LIMIT 1",
                "withdrawalId", "SELECT id FROM withdrawal WHERE is_active = true LIMIT 1",
                "replenishmentId", "SELECT id FROM replenishment WHERE is_active = true LIMIT 1",
                "capitalizationId", "SELECT id FROM capitalization WHERE is_active = true LIMIT 1"
        );

        try {
            for (Map.Entry<String, String> entry : queries.entrySet()) {
                try (PreparedStatement stmt = connection.prepareStatement(entry.getValue());
                     ResultSet resultSet = stmt.executeQuery()) {
                    if (resultSet.next()) {
                        ids.put(entry.getKey(), resultSet.getObject("id").toString());
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ids;
    }

    public static boolean deleteByNamePrefixAndCheck(Connection connection, String namePrefix) {
        if (connection == null) {
            return false;
        }

        String deleteQuery = "DELETE FROM time_deposit_product WHERE name LIKE ?";
        String checkQuery = "SELECT count(*) FROM time_deposit_product WHERE name LIKE ?";

        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, namePrefix + "%");
            deleteStatement.executeUpdate();
        } catch (SQLException exception) {
            return false;
        }

        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             ResultSet deleteCheckSet = checkStatement.executeQuery()) {
            return deleteCheckSet.next() && deleteCheckSet.getInt(1) == 0;
        } catch (SQLException exception) {
            return false;
        }
    }

    // Практичнее использовать queryForObject из Spring JDBC для упрощения кода
    public static boolean isProductExists(Connection connection, String name) {
        String query = "SELECT COUNT(*) FROM time_deposit_product WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<String> getArchiveCredProds(Connection connection) {
        List<String> queryResult = new ArrayList<>();
        String query = "SELECT id FROM public.credit_product WHERE in_archive = true;";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                queryResult.add(resultSet.getString("id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return queryResult;
    }

    public static void updateArchiveCredProds(Connection connection, List<String> list, boolean isArchive) {
        String query = "UPDATE public.credit_product SET in_archive = ? WHERE id = ?::uuid;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (String id : list) {
                statement.setBoolean(1, isArchive);
                statement.setString(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static UUID addArchiveCredProds(Connection connection) {
        UUID uuid = UUID.randomUUID();
        String query = "INSERT INTO public.credit_product " +
                "(id, credit_type, description, min_period_months, max_period_months, min_sum, max_sum, " +
                "loan_collateral, constant_rate, currency_code, early_repayment, is_active, picture_name, " +
                "loan_guarantors, credit_insurance, creation_date, in_archive) " +
                "VALUES(?, 'PERSONAL', 'Кредит наличными', 6, 36, 50, 500000, false, 29.9, 'RUB', " +
                "false, false, 'Name', false, false, '2023-07-01', true);";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, uuid);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return uuid;
    }

    public static void deleteArchiveCredProds(Connection connection, UUID id) {
        String query = "DELETE FROM public.credit_product WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static String getFirstEmployeeId(Connection connection) {
        String query = "SELECT id FROM employee LIMIT 1;";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}