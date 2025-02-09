package in.samintejas.v3;

import in.samintejas.v3.parser.at.ATData;
import in.samintejas.v3.parser.at.ATParser;
import in.samintejas.v3.parser.fed.FEDData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FlowBuilder {


    public static Flow build(FEDData fedData) throws IOException {
        Flow flow = new Flow();
        ApiNode rootNode = buildApiNode(fedData);
        fillApiNodeTree(rootNode, fedData);
        flow.setRoot(rootNode);
        return flow;
    }


    private static ApiNode buildApiNode(FEDData fedData) throws IOException {

        ApiNode apiNode = new ApiNode();
        apiNode.setName(fedData.getName());
        apiNode.setCount(fedData.getCount());
        apiNode.setDependsOn(fedData.getDependsOn());
        String file = "/home/samin/repo/samintejas.in/flow-engine/src/main/resources/api-templates/" + fedData.getApiTemplate() + ".at";
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
