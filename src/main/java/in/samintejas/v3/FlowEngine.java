package in.samintejas.v3;

import in.samintejas.v3.parser.fed.FEDData;
import in.samintejas.v3.parser.fed.FEDParser;

import java.io.IOException;

public class FlowEngine implements AutoCloseable{

    private final FEDParser fedParser;
    private final Traveller traveller;

    public FlowEngine(FEDParser fedParser, Traveller traveller) {
        this.fedParser = fedParser;
        this.traveller = traveller;
    }

    public void executeFlow(String flowPath) throws IOException {
        FEDData fedData = fedParser.parseFile(flowPath);
        Flow flow = FlowBuilder.build(fedData);
        flow.setName("Onboarding journey");

        traveller.traverseDepthFirst(flow, new DefaultNodeProcessor());
    }

    @Override
    public void close() throws Exception {

    }
}
