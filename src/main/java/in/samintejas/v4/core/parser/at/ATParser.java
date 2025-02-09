package in.samintejas.v4.core.parser.at;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ATParser {

    private static final String METADATA_DELIMITER = "-- metadata";
    private static final String BODY_DELIMITER = "-- body";
    private static final String METADATA_SEPARATOR = ":";
    private static final String COMMENT_PREFIX = "//";

    public static ATData parseFromFile(String configFilePath) throws IOException {

        Path path = Paths.get(configFilePath);
        validateFile(path);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return parseContent(reader);
        }
    }

    private static void validateFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Configuration file does not exist: " + path);
        }
        if (!Files.isReadable(path)) {
            throw new IOException("Configuration file is not readable: " + path);
        }
    }

    private static ATData parseContent(BufferedReader reader) throws IOException {

        Map<String, String> metadataMap = new HashMap<>();
        StringBuilder body = new StringBuilder();
        Section currentSection = Section.NONE;

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith(COMMENT_PREFIX)) {
                continue;
            }

            if (line.equals(METADATA_DELIMITER)) {
                currentSection = Section.METADATA;
                continue;
            } else if (line.equals(BODY_DELIMITER)) {
                currentSection = Section.BODY;
                continue;
            }

            switch (currentSection) {
                case METADATA:
                    processMetadataLine(line, metadataMap);
                    break;
                case BODY:
                    body.append(line).append("\n");
                    break;
                case NONE:
                    throw new ATDataParserException("Flow engine config file started with unknown section");
            }
        }

        return new ATData(metadataMap, body.toString().trim());
    }

    private static void processMetadataLine(String line, Map<String, String> metadataMap) {
        int separatorIndex = line.indexOf(METADATA_SEPARATOR);
        if (separatorIndex > 0) {
            String key = line.substring(0, separatorIndex).trim();
            String value = line.substring(separatorIndex + 1).trim();

            if (!key.isEmpty() && !value.isEmpty()) {
                metadataMap.put(key, value);
            }
        }
    }

    private enum Section {
        NONE,
        METADATA,
        BODY
    }

}
