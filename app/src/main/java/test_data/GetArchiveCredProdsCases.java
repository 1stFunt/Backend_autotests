package test_data;

import providers.authenticationService.AuthProvider;
import utilities.ResponseUtils;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import static data.constants.creditService.GetCredProdsConstants.TOKEN_PATH;
import static utilities.JDBCUtils.*;

public class GetArchiveCredProdsCases {

    public static List<String> getArchiveCredProdsFromDB(Connection connection) {
        return getArchiveCredProds(connection);
    }

    public static void updateValueInArchive(Connection connection, List<String> list, boolean isArchive) {
        if (!list.isEmpty()) {
            updateArchiveCredProds(connection, list, isArchive);
        }
    }

    public static UUID addArchiveCredProdsInDB(Connection connection, List<String> list) {
        if (list.isEmpty()) {
            return addArchiveCredProds(connection);
        }
        return null;
    }

    public static void deleteArchiveCredProdsFromDB(Connection connection, List<String> list, UUID id) {
        if (list.isEmpty()) {
            deleteArchiveCredProds(connection, id);
        }
    }

    public static String positiveTestData(AuthProvider AUTH_PROVIDER) {
        return ResponseUtils.getResponseValToString(AUTH_PROVIDER.postPositiveRequestAuth(), TOKEN_PATH);
    }

    public static String negativeIncTokTestData(AuthProvider AUTH_PROVIDER) {
        return ResponseUtils.getResponseValToString(AUTH_PROVIDER.postPositiveRequestAuth(), TOKEN_PATH) + "defect";
    }
}