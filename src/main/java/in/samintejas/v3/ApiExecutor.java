package in.samintejas.v3;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ApiExecutor {


    private final RestAPI restAPI;
    private final Map<String,String> context;

    public ApiExecutor(RestAPI restAPI, Map<String, String> context) {
        this.restAPI = restAPI;
        this.context = context;
    }


    public Map<String,String> execute(){
        restAPI.buildEmptyPlaceholderMap();
        log.info(restAPI.generateNewRequest(context));
        return new HashMap<>();
    }
}
