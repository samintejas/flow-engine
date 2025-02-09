package in.samintejas.v4.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatter {

    public static String formatPretty(String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
    }

    public static String formatMinified(String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        return mapper.writeValueAsString(jsonNode);
    }
}
