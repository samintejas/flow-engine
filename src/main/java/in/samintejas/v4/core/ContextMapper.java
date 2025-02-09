package in.samintejas.v4.core;

import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class ContextMapper {

    /**
     * Maps values from the source context to the template context based on matching keys.
     * If a required template key is not found in the source context, it's left with its original value.
     *
     * @param templateContext The context with placeholder values that need to be filled
     * @param sourceContext The context containing the actual values
     * @return A new map containing the mapped values
     */
    public Map<String, String> mapContext(Map<String, String> templateContext, Map<String, String> sourceContext) {
        validateContexts(templateContext, sourceContext);

        Map<String, String> mappedContext = new HashMap<>(templateContext);

        templateContext.forEach((key, value) -> {
            try {
                mapSingleContext(mappedContext, sourceContext, key);
            } catch (Exception e) {
                log.warn("Failed to map context for key: {}. Error: {}", key, e.getMessage());
            }
        });

        logMappingResults(templateContext, mappedContext);
        return mappedContext;
    }

    /**
     * Maps values with transformation support.
     *
     * @param templateContext The template context
     * @param sourceContext The source context
     * @param transformer A function to transform values during mapping
     * @return Mapped context with transformed values
     */
    public Map<String, String> mapContextWithTransform(
            Map<String, String> templateContext,
            Map<String, String> sourceContext,
            ValueTransformer transformer) {

        Map<String, String> mappedContext = new HashMap<>(templateContext);

        templateContext.forEach((key, value) -> {
            Optional.ofNullable(sourceContext.get(key))
                    .map(transformer::transform)
                    .ifPresent(transformedValue -> mappedContext.put(key, transformedValue));
        });

        return mappedContext;
    }

    private void validateContexts(Map<String, String> templateContext, Map<String, String> sourceContext) {
        if (templateContext == null) {
            throw new IllegalArgumentException("Template context cannot be null");
        }
        if (sourceContext == null) {
            throw new IllegalArgumentException("Source context cannot be null");
        }
    }

    private void mapSingleContext(Map<String, String> mappedContext,
                                  Map<String, String> sourceContext,
                                  String key) {
        String sourceValue = sourceContext.get(key);
        if (sourceValue != null) {
            mappedContext.put(key, sourceValue);
            log.debug("Mapped key '{}' to value '{}'", key, sourceValue);
        } else {
            log.debug("No mapping found for key '{}', keeping original value", key);
        }
    }

    private void logMappingResults(Map<String, String> templateContext,
                                   Map<String, String> mappedContext) {
        if (log.isDebugEnabled()) {
            int mappedCount = (int) mappedContext.entrySet().stream()
                    .filter(e -> !e.getValue().equals(templateContext.get(e.getKey())))
                    .count();

            log.debug("Context mapping completed. Mapped {} out of {} keys",
                    mappedCount, templateContext.size());
        }
    }

    /**
     * Gets unmapped keys (keys that weren't found in the source context).
     *
     * @param templateContext The template context
     * @param mappedContext The mapped context
     * @return Set of unmapped keys
     */
    public Set<String> getUnmappedKeys(Map<String, String> templateContext,
                                       Map<String, String> mappedContext) {
        Set<String> unmappedKeys = new HashSet<>();

        templateContext.forEach((key, originalValue) -> {
            if (originalValue.equals(mappedContext.get(key))) {
                unmappedKeys.add(key);
            }
        });

        return Collections.unmodifiableSet(unmappedKeys);
    }

}
