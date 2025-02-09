package in.samintejas.v4.core.parser.fed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FEDParser {

    private final ObjectMapper objectMapper;

    public FEDParser() {
        this.objectMapper = new ObjectMapper();
    }

    public FEDData parse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, FEDData.class);
    }

    public FEDData parseFile(File file) throws IOException {
        return objectMapper.readValue(file, FEDData.class);
    }

    public FEDData parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = Files.readString(path);
        return parse(content);
    }

}
