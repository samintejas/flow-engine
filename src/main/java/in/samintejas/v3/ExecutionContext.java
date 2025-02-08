package in.samintejas.v3;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class ExecutionContext {

    private final Map<String, String> context;

    public ExecutionContext(Map<String, String> context) {
        this.context = context;
    }

    public static ExecutionContext from(ExecutionContext executionContext) {
        return new ExecutionContext(executionContext.getContext());
    }
}
