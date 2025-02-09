package in.samintejas.v4.core.model;


import in.samintejas.v4.core.parser.fed.DependencyContext;
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
