package in.samintejas.v4.core;

import in.samintejas.v4.core.builder.FlowBuilder;
import in.samintejas.v4.core.model.Flow;
import in.samintejas.v4.core.parser.fed.FEDData;
import in.samintejas.v4.core.parser.fed.FEDParser;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class FlowEngine implements AutoCloseable {

    private final FEDParser fedParser;
    private final Traveller traveller;

    public FlowEngine() {
        this.fedParser = new FEDParser();
        this.traveller = new Traveller();
    }

    public void executeFlow(String flowPath) throws IOException {

        FEDData fedData = fedParser.parseFile(flowPath);
        Flow flow = FlowBuilder.build(fedData);
        flow.setName("Onboarding journey");

        traveller.traverseDepthFirst(flow, new DefaultNodeProcessor());
    }

    @Override
    public void close() {
        // Cleanup resources if needed
    }
}
