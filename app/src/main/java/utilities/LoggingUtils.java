package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LoggingUtils {

    public static final Logger LOGGER = Logger.getLogger(LoggingUtils.class.getName());
    public static final String LOG_FILE_PATH = "src/main/resources/logs/jdbc_utils.log";

    private LoggingUtils() {
    }

    public static void setupLogger() {
        try {
            clearLogs();
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка настройки логирования в файл", e);
        }
    }

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logWarning(String message) {
        LOGGER.warning(message);
    }

    public static void logSevere(String message, Exception exception) {
        LOGGER.log(Level.SEVERE, message, exception);
    }

    public static boolean checkConnection(Connection connection) {
        if (connection == null) {
            logWarning("Соединение с БД отсутствует");
            return false;
        }
        return true;
    }

    public static void clearLogs() {
        try {
            Files.write(Paths.get(LOG_FILE_PATH), new byte[0]);
        } catch (IOException e) {
            logSevere("Ошибка очистки логов", e);
        }
    }
}
