package in.samintejas.v3;

import in.samintejas.v3.util.FlowPrinter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.function.BiConsumer;

// TODO : better algo can be implemented for DFS
@Log4j2
public class Traveller {

    private static void printPreExecutionDetails(Flow flow) {
        log.info("Starting DFS traversal of the flow : {}",flow.getName());
        FlowPrinter.printFlow(flow);
    }

    public void traverseDepthFirst(Flow flow, BiConsumer<ApiNode, ExecutionContext> nodeProcessor) {

        if (flow == null || flow.getRoot() == null) {
            throw new RuntimeException("Flow is empty or root node is empty");
        }

        printPreExecutionDetails(flow);
        ExecutionContext context = new ExecutionContext(new HashMap<>(flow.getGlobalDependencyContext()));
        traverseNode(flow.getRoot(), context, nodeProcessor);

    }

    private void traverseNode(ApiNode node, ExecutionContext parentContext,
                              BiConsumer<ApiNode, ExecutionContext> nodeProcessor) {
        if (node == null) {
            return;
        }

        for(int i = 0; i < node.getCount(); i++) {

            ExecutionContext context = ExecutionContext.from(parentContext);
            nodeProcessor.accept(node, context);

            // now context have response

            if (node.getNextNode() != null) {

                // todo : take response and resolve only required one based on depends on

                for (ApiNode childNode : node.getNextNode()) {
                    traverseNode(childNode, context, nodeProcessor);
                }
            }
        }
    }

}
