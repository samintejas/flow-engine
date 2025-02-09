package in.samintejas.v4.core;

import in.samintejas.v4.core.exceptions.DependencyResolutionException;
import in.samintejas.v4.core.model.ApiNode;
import in.samintejas.v4.core.parser.fed.DependencyContext;
import in.samintejas.v4.core.util.JsonValueExtractor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DependencyResolver {

    private final JsonValueExtractor jsonValueExtractor;

    public DependencyResolver(JsonValueExtractor jsonValueExtractor) {
        this.jsonValueExtractor = jsonValueExtractor;
    }

    public void resolveDependencies(ApiNode node, ExecutionContext context) {

        if (!hasDependencies(node)) {
            log.debug("{} node is independent", node.getName());
            return;
        }

        for (DependencyContext dependencyContext : node.getDependsOn()) {
            try {
                resolveSingleDependency(dependencyContext, context);
            } catch (Exception e) {
                log.error("Failed to resolve dependency {} for node {}",
                        dependencyContext.getProperty(), node.getName(), e);
                throw new DependencyResolutionException(
                        "Failed to resolve dependency: " + dependencyContext.getProperty(), e);
            }
        }
    }

    private boolean hasDependencies(ApiNode node) {
        return node.getDependsOn() != null && !node.getDependsOn().isEmpty();
    }

    private void resolveSingleDependency(DependencyContext dependencyContext, ExecutionContext context) {

        log.debug("Resolving dependency: {}", dependencyContext);
        String sourceResponse = getSourceResponse(dependencyContext, context);
        String extractedValue = extractValue(sourceResponse, dependencyContext);
        context.getContext().put(dependencyContext.getProperty(), extractedValue);
        log.debug("Resolved value '{}' for property '{}'", extractedValue, dependencyContext.getProperty());
    }

    private String getSourceResponse(DependencyContext dependencyContext, ExecutionContext context) {
        String sourceResponse = context.getResponse().get(dependencyContext.getSource());
        if (sourceResponse == null) {
            throw new DependencyResolutionException(
                    "Source response not found: " + dependencyContext.getSource());
        }
        return sourceResponse;
    }

    private String extractValue(String sourceResponse, DependencyContext dependencyContext) {
        String extractedValue = jsonValueExtractor.extractValue(
                sourceResponse,
                dependencyContext.getProperty()
        );

        if (extractedValue == null) {
            throw new DependencyResolutionException(
                    "Failed to extract value for property: " + dependencyContext.getProperty());
        }

        return extractedValue;
    }

}
