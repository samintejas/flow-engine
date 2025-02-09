package in.samintejas.v4.core.parser.fed;

import lombok.Data;

import java.util.List;

@Data
public class FEDData {

    private String name;
    private Integer count;
    private String apiTemplate;
    private List<FEDData> nextNode;
    private List<DependencyContext> dependsOn;
}
