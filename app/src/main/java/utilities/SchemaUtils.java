package utilities;

public class SchemaUtils {

    private SchemaUtils() {
    }

    private static final String PATH = "json_schemas/";

    public static String getSchema(String fileName) {
        return PATH + fileName;
    }
}
