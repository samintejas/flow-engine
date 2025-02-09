package in.samintejas.v4.core;

import in.samintejas.v4.core.model.RestAPI;
import in.samintejas.v4.core.util.CurlRequestGenerator;
import in.samintejas.v4.core.util.JsonFormatter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class ApiExecutor {

    private final RestAPI restAPI;
    private final ExecutionContext context;
    private final ContextMapper contextMapper;

    public ApiExecutor(RestAPI restAPI, ExecutionContext context) {
        this.restAPI = restAPI;
        this.context = context;
        this.contextMapper = new ContextMapper();
    }

    public ExecutionContext execute() {
        Map<String, String> mappedContext = contextMapper.mapContext(
                restAPI.buildEmptyPlaceholderMap(),
                context.getContext()
        );

        String response = restAPI.generateNewRequest(mappedContext);
        try{
            String resp = JsonFormatter.formatMinified(response);
            log.debug("generated req {}", resp);
            List<String> headers = new ArrayList<>();
            CurlRequestGenerator.logCurlRequest(CurlRequestGenerator.generatePostCurlRequest(restAPI.getUrl(),restAPI.getMethod(), resp,headers));
        }catch (Exception e){
            log.info("Error while printing req");
        }

        context.getResponse().put(restAPI.getName(), response);
        return context;
    }
}
