package in.samintejas.v4.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

//TODO functionality to extract array
@Log4j2
public class JsonValueExtractor {

    private final ObjectMapper objectMapper;

    public JsonValueExtractor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String extractValue(String jsonString, String jsonPath) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);

            String[] pathParts = jsonPath.split("\\.");
            JsonNode currentNode = rootNode;

            for (String part : pathParts) {
                currentNode = currentNode.get(part);
                if (currentNode == null) {
                    log.warn("Path {} not found in JSON", jsonPath);
                    return null;
                }
            }

            return currentNode.asText();
        } catch (Exception e) {
            log.error("Failed to extract value for path: {}", jsonPath, e);
            return null;
        }
    }

}
