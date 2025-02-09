package in.samintejas.v4.core;

public class CommonTransformers {

    public static final ValueTransformer UPPERCASE = String::toUpperCase;
    public static final ValueTransformer LOWERCASE = String::toLowerCase;
    public static final ValueTransformer TRIM = String::trim;

    public static ValueTransformer prefix(String prefix) {
        return value -> prefix + value;
    }

    public static ValueTransformer suffix(String suffix) {
        return value -> value + suffix;
    }

}
