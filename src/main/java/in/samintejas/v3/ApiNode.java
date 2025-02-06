package in.samintejas.v3;

import lombok.Data;

import java.util.List;

@Data
public class ApiNode {

    private String name;
    private RestAPI restAPI;
    private List<ApiNode> nextNode;
    private Integer count;

}
