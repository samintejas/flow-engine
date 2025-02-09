package in.samintejas.v4.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrefixRemovingTransformer implements ValueTransformer{

    private final String prefix;
    private final ValueTransformer delegate;

    @Override
    public String transform(String value) {
        String keyWithoutPrefix = value.startsWith(prefix) ?
                value.substring(prefix.length()) : value;
        return delegate.transform(keyWithoutPrefix);
    }

}
