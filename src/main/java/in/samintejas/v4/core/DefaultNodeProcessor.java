package in.samintejas.v4.core;

import in.samintejas.v4.core.model.ApiNode;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultNodeProcessor implements NodeProcessor{

    private final ApiExecutorFactory apiExecutorFactory;

    public DefaultNodeProcessor() {
        this.apiExecutorFactory = new ApiExecutorFactory();
    }

    @Override
    public void process(ApiNode node, ExecutionContext context) {
        log.info("Executing node: {}", node.getName());
        if (node.getRestAPI() != null) {
            ApiExecutor executor = apiExecutorFactory.createExecutor(node.getRestAPI(), context);
            context = executor.execute();
        }
        log.info("Execution complete: {}", node.getName());
    }

}
