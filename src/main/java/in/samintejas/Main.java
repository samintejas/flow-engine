package in.samintejas;

import in.samintejas.v3.ApiNode;
import in.samintejas.v3.Flow;
import in.samintejas.v3.HttpMethod;
import in.samintejas.v3.RestAPI;
import in.samintejas.v3.parser.at.ATData;
import in.samintejas.v3.parser.at.ATParser;
import in.samintejas.v3.parser.fed.FEDData;
import in.samintejas.v3.parser.fed.FEDParser;
import in.samintejas.v3.util.FlowPrinter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {


        try {

            FEDParser fedParser = new FEDParser();
            FEDData fedData = fedParser.parseFile("/home/samin/sixdee/dev-tools/flow-engine/flow-engine/src/main/resources/flow/onboarding.json");

            Flow flow = new Flow();
            ApiNode rootNode = buildApiNode(fedData);
            fillApiNodeTree(rootNode, fedData);
            flow.setRoot(rootNode);

            FlowPrinter.printFlow(flow);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static ApiNode buildApiNode(FEDData fedData) throws IOException {

        ApiNode apiNode = new ApiNode();
        apiNode.setName(fedData.getName());
        apiNode.setCount(fedData.getCount());
        String file = "/home/samin/sixdee/dev-tools/flow-engine/flow-engine/src/main/resources/api-templates/" + fedData.getApiTemplate() + ".at";
        ATData fecData = ATParser.parseFromFile(file);
        RestAPI api = new RestAPI(
                fecData.getMetadata().get("name"),
                fecData.getMetadata().get("url"),
                HttpMethod.valueOf(fecData.getMetadata().get("method")),
                fecData.getBody());
        apiNode.setRestAPI(api);

        return apiNode;

    }

    private static void fillApiNodeTree(ApiNode currentNode, FEDData fedData) throws IOException {

        if (fedData.getNextNode() == null || fedData.getNextNode().isEmpty()) {
            return;
        }

        List<ApiNode> nextNodes = new ArrayList<>();

        for (FEDData nextFedData : fedData.getNextNode()) {
            ApiNode nextNode = buildApiNode(nextFedData);
            fillApiNodeTree(nextNode, nextFedData);
            nextNodes.add(nextNode);
        }

        currentNode.setNextNode(nextNodes);
    }
}