package in.samintejas.v4.core.parser.at;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ATData {

    private Map<String,String> metadata;
    private String body;
}
