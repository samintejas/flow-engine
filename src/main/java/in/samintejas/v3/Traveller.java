package in.samintejas.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.samintejas.v3.parser.fed.DependencyContext;
import in.samintejas.v3.util.FlowPrinter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.function.BiConsumer;

// TODO : better algo can be implemented for DFS
@Log4j2
public class Traveller {

    ObjectMapper objectMapper;
    JsonValueExtractor jsonValueExtractor;

    public Traveller() {
        this.objectMapper = new ObjectMapper();
        this.jsonValueExtractor = new JsonValueExtractor(objectMapper);
    }

    private static void printPreExecutionDetails(Flow flow) {
        log.info("Starting DFS traversal of the flow : {}",flow.getName());
        FlowPrinter.printFlow(flow);
    }

    public void traverseDepthFirst(Flow flow, BiConsumer<ApiNode, ExecutionContext> nodeProcessor) {

        if (flow == null || flow.getRoot() == null) {
            throw new RuntimeException("Flow is empty or root node is empty");
        }

        // printPreExecutionDetails(flow);
        ExecutionContext context = ExecutionContext.empty();
        traverseNode(flow.getRoot(), context, nodeProcessor);

    }

    private void traverseNode(ApiNode node, ExecutionContext parentContext,
                              BiConsumer<ApiNode, ExecutionContext> nodeProcessor) {
        if (node == null) {
            return;
        }

        for(int i = 0; i < node.getCount(); i++) {

            ExecutionContext context = ExecutionContext.from(parentContext);

            if(node.getDependsOn() != null && !node.getDependsOn().isEmpty()) {
                for (DependencyContext dependencyContext : node.getDependsOn()) {
                    log.info("node.getDependsOn() {}",node.getDependsOn());
                    String prevResp = context.getResponse().get(dependencyContext.getSource());
                    log.info("prevResp {}",prevResp);
                    log.info("dependencyContext.getProperty() {}",dependencyContext.getProperty());
                    String val = jsonValueExtractor.extractValue(prevResp,dependencyContext.getProperty());
                    log.info("val {}",val);
                    context.getContext().put(dependencyContext.getProperty(),val);
                }
            } else {
                log.info("No dependencies for node {} ",node.getName());
            }

            nodeProcessor.accept(node, context);

            if (node.getNextNode() != null) {
                for (ApiNode childNode : node.getNextNode()) {
                    traverseNode(childNode, context, nodeProcessor);
                }
            }
        }
    }

}
