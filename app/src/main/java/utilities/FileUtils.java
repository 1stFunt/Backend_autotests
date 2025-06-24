package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    private FileUtils() {
    }

    public static void writeToJsonFile(String fileName, Object testData) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String mappedData = mapper.writeValueAsString(testData);
            writer.write(mappedData);
            writer.flush();
        } catch (IOException exception) {
            exception.getLocalizedMessage();
        }
    }

    public static String getJsonMapped(File json) {
        String mappedFile = "";
        try {
            mappedFile = org.apache.commons.io.FileUtils.readFileToString(json, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            exception.getLocalizedMessage();
        }
        return mappedFile;
    }
}