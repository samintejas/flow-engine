package in.samintejas.v3;

import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ApiExecutor {


    private final RestAPI restAPI;
    private final ExecutionContext context;

    public ApiExecutor(RestAPI restAPI, ExecutionContext context) {
        this.restAPI = restAPI;
        this.context = context;
    }


    public ExecutionContext execute(){

        Map<String,String> emptyContext = restAPI.buildEmptyPlaceholderMap();
        Map<String,String> passedCtx = context.getContext();
        log.debug("fields required by the template : {}",emptyContext);
        log.debug("passed in fields from context : {}",passedCtx);
        emptyContext.forEach((ctx,val) -> {
            if(passedCtx.get(ctx) != null) {
                emptyContext.put(ctx,passedCtx.get(ctx));
            }
        });
        context.getResponse().put(restAPI.getName(),restAPI.generateNewRequest(emptyContext));
        return context;
    }
}
