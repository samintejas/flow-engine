package in.samintejas.v4.core;

import in.samintejas.v4.core.util.DataFaker;

import java.util.HashMap;
import java.util.Map;

public class TransformerRegistry {

    private final Map<String, ValueTransformer> transformers = new HashMap<>();

    public TransformerRegistry() {
        registerDefaultTransformers();
    }

    private void registerDefaultTransformers() {

        register("$d-", DataFaker::generateRandomTime);
        register("$r-", DataFaker::generateRandomVal);

        // Identity transformer (default)
        register("", value -> value);
    }

    public void register(String prefix, ValueTransformer transformer) {
        transformers.put(prefix, transformer);
    }

//    public ValueTransformer getTransformer(String key) {
//        return transformers.entrySet().stream()
//                .filter(entry -> key.startsWith(entry.getKey()))
//                .findFirst()
//                .map(entry -> new PrefixRemovingTransformer(entry.getKey(), entry.getValue()))
//                .orElse(value -> value); // Default identity transformer
//    }

}
