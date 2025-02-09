package in.samintejas.v4.core;

import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class TemplateProcessor {

    private final TransformerRegistry transformerRegistry;
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)}}");

    public TemplateProcessor() {
        this.transformerRegistry = new TransformerRegistry();
    }

//    public String generateNewRequest(String template, Map<String, String> params) {
//        if (template == null || params == null) {
//            throw new IllegalArgumentException("Template and params cannot be null");
//        }
//
//        StringBuilder result = new StringBuilder();
//        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
//
//        while (matcher.find()) {
//            String placeholder = matcher.group(1);
//            String value = processPlaceholder(placeholder, params);
//            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
//        }
//        matcher.appendTail(result);
//
//        return result.toString();
//    }

//    private String processPlaceholder(String placeholder, Map<String, String> params) {
//        try {
//            String paramValue = params.getOrDefault(placeholder, "");
//            ValueTransformer transformer = transformerRegistry.getTransformer(placeholder);
//            return transformer.transform(paramValue);
//        } catch (Exception e) {
//            log.error("Error processing placeholder: {}", placeholder, e);
//            return "";
//        }
//    }

    // Method to register custom transformers
    public void registerTransformer(String prefix, ValueTransformer transformer) {
        transformerRegistry.register(prefix, transformer);
    }

}
