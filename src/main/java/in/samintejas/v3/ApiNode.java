package in.samintejas.v3;

import in.samintejas.v3.parser.fed.DependencyContext;
import lombok.Data;

import java.util.List;

@Data
public class ApiNode {

    private String name;
    private RestAPI restAPI;
    private List<ApiNode> nextNode;
    private Integer count;
    private List<DependencyContext> dependsOn;

}
