package in.samintejas.v3.util;

import in.samintejas.v3.ApiNode;
import in.samintejas.v3.Flow;
import in.samintejas.v3.RestAPI;

public class FlowPrinter {

    private static final String INDENT = "    "; // 4 spaces for each level

    public static void printFlow(Flow flow) {
        if (flow == null || flow.getRoot() == null) {
            System.out.println("Empty flow");
            return;
        }

        System.out.println("Flow tree:");
        System.out.println("===================");
        printNode(flow.getRoot(), 0);

        // Print global dependency context if present
        if (flow.getGlobalDependencyContext() != null && !flow.getGlobalDependencyContext().isEmpty()) {
            System.out.println("\nGlobal Dependencies:");
            System.out.println("===================");
            flow.getGlobalDependencyContext().forEach((key, value) ->
                    System.out.println(INDENT + key + " -> " + value));
        }
    }

    private static void printNode(ApiNode node, int depth) {
        // Print indentation
        String indent = INDENT.repeat(depth);

        // Print node details
        System.out.println(indent + "├── Node: " + node.getName());

        // Print REST API details if present
        if (node.getRestAPI() != null) {
            RestAPI api = node.getRestAPI();
            System.out.println(indent + "│   ├── API Name: " + api.getName());
            System.out.println(indent + "│   ├── URL: " + api.getUrl());
            System.out.println(indent + "│   ├── Method: " + api.getMethod());
            System.out.println(indent + "*   └── Count: " + node.getCount());

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
