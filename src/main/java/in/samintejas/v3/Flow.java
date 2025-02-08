package in.samintejas.v3;

import lombok.Data;

import java.util.Map;

@Data
public class Flow {

    private String name;
    private ApiNode root;
    private Map<String,String> globalDependencyContext;

}
