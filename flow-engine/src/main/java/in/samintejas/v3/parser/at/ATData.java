package in.samintejas.v3.parser.at;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ATData {

    private Map<String,String> metadata;
    private String body;
}
