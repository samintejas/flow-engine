package in.samintejas;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.samintejas.v3.*;
import in.samintejas.v3.parser.at.ATData;
import in.samintejas.v3.parser.at.ATParser;
import in.samintejas.v3.parser.fed.DependencyContext;
import in.samintejas.v3.parser.fed.FEDData;
import in.samintejas.v3.parser.fed.FEDParser;
import in.samintejas.v3.util.FlowPrinter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class Main {

    public static void main(String[] args) {


        try {

            FEDParser fedParser = new FEDParser();
            FEDData fedData = fedParser.parseFile("/home/samin/repo/samintejas.in/flow-engine/src/main/resources/flow/onboarding.json");
            Flow flow = FlowBuilder.build(fedData);
            flow.setName("Onboarding journey");

            Traveller traveller = new Traveller();
            traveller.traverseDepthFirst(flow, (node, context) -> {

                log.info("Executing node: {}", node.getName());
                if (node.getRestAPI() != null) {
                    ApiExecutor executor = new ApiExecutor(node.getRestAPI(),context);
                    context = executor.execute();
                }
                log.info("Execution complete: {}", node.getName());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}