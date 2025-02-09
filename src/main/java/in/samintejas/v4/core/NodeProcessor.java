package in.samintejas.v4.core;


import in.samintejas.v4.core.model.ApiNode;

public interface NodeProcessor {
    void process(ApiNode node, ExecutionContext context);
}
