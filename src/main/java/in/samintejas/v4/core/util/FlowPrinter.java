package in.samintejas.v4.core.util;

import in.samintejas.v3.ApiNode;
import in.samintejas.v3.Flow;
import in.samintejas.v3.RestAPI;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FlowPrinter {

    private static final String INDENT = "    ";

    public static void printFlow(Flow flow) {
        if (flow == null || flow.getRoot() == null) {
            log.warn("Flow or root node in the flow is empty , please check flow config");
            return;
        }

        log.info("Flow tree:");
        log.info("===================");
        printNode(flow.getRoot(), 0);

        // Print global dependency context if present
        if (flow.getGlobalDependencyContext() != null && !flow.getGlobalDependencyContext().isEmpty()) {
            log.info("\nGlobal Dependencies:");
            log.info("===================");
            flow.getGlobalDependencyContext().forEach((key, value) ->
                    log.info(INDENT + key + " -> " + value));
        }
    }

    private static void printNode(ApiNode node, int depth) {
        // Print indentation
        String indent = INDENT.repeat(depth);

        // Print node details
        log.info(indent + "├── Node: " + node.getName());

        // Print REST API details if present
        if (node.getRestAPI() != null) {
            RestAPI api = node.getRestAPI();
            log.info(indent + "│   ├── API Name: " + api.getName());
            log.info(indent + "│   ├── URL: " + api.getUrl());
            log.info(indent + "│   ├── Method: " + api.getMethod());
            log.info(indent + "*   └── Count: " + node.getCount());

        }

        // Print child nodes
        if (node.getNextNode() != null && !node.getNextNode().isEmpty()) {
            for (int i = 0; i < node.getNextNode().size(); i++) {
                ApiNode nextNode = node.getNextNode().get(i);
                printNode(nextNode, depth + 1);
            }
        }
    }

}
