package in.samintejas.v4.core;

public class PrefixStripperTransformer implements ValueTransformer{

    private final String prefix;
    private final ValueTransformer delegate;

    public PrefixStripperTransformer(String prefix, ValueTransformer delegate) {
        this.prefix = prefix;
        this.delegate = delegate;
    }

    @Override
    public String transform(String value) {
        return delegate.transform(value.startsWith(prefix) ?
                value.substring(prefix.length()) : value);
    }

}
