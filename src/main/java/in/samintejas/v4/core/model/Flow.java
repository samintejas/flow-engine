package in.samintejas.v4.core.model;


import lombok.Data;

import java.util.Map;

@Data
public class Flow {

    private String name;
    private ApiNode root;
    private Map<String,String> globalDependencyContext;

}
