package in.samintejas;

import in.samintejas.v4.core.FlowEngine;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {


    public static void main(String[] args) {

        try (var flowEngine = new FlowEngine()) {
            flowEngine.executeFlow("/home/samin/repo/samintejas.in/flow-engine/src/main/resources/flow/onboarding.json");
        } catch (Exception e) {
            log.error("Flow execution failed", e);
            System.exit(1);
        }
    }

//    public static void main(String[] args) {
//
//
//        try {
//
//            FEDParser fedParser = new FEDParser();
//            FEDData fedData = fedParser.parseFile("/home/samin/repo/samintejas.in/flow-engine/src/main/resources/flow/onboarding.json");
//            Flow flow = FlowBuilder.build(fedData);
//            flow.setName("Onboarding journey");
//
//            Traveller traveller = new Traveller();
//            traveller.traverseDepthFirst(flow, (node, context) -> {
//
//                log.info("Executing node: {}", node.getName());
//                if (node.getRestAPI() != null) {
//                    ApiExecutor executor = new ApiExecutor(node.getRestAPI(),context);
//                    context = executor.execute();
//                }
//                log.info("Execution complete: {}", node.getName());
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }



}