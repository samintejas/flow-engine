package in.samintejas.v4.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.samintejas.v4.core.exceptions.FlowValidationException;
import in.samintejas.v4.core.model.ApiNode;
import in.samintejas.v4.core.model.Flow;
import in.samintejas.v4.core.util.JsonValueExtractor;

public class Traveller {
    private final ObjectMapper objectMapper;
    private final JsonValueExtractor jsonValueExtractor;
    private final DependencyResolver dependencyResolver;  // New class for handling dependencies

    public Traveller() {
        this.objectMapper = new ObjectMapper();
        this.jsonValueExtractor = new JsonValueExtractor(objectMapper);
        this.dependencyResolver = new DependencyResolver(jsonValueExtractor);
    }

    public void traverseDepthFirst(Flow flow, NodeProcessor nodeProcessor) {
        validateFlow(flow);
        ExecutionContext context = ExecutionContext.empty();
        traverseNode(flow.getRoot(), context, nodeProcessor);
    }

    private void validateFlow(Flow flow) {
        if (flow == null || flow.getRoot() == null) {
            throw new FlowValidationException("Flow is empty or root node is empty");
        }
    }

    private void traverseNode(ApiNode node, ExecutionContext parentContext, NodeProcessor nodeProcessor) {
        if (node == null) return;

        for (int i = 0; i < node.getCount(); i++) {
            ExecutionContext context = ExecutionContext.from(parentContext);
            dependencyResolver.resolveDependencies(node, context);
            nodeProcessor.process(node, context);
            traverseChildren(node, context, nodeProcessor);
        }
    }

    private void traverseChildren(ApiNode node, ExecutionContext context, NodeProcessor nodeProcessor) {
        if (node.getNextNode() != null) {
            for (ApiNode childNode : node.getNextNode()) {
                traverseNode(childNode, context, nodeProcessor);
            }
        }
    }

}
