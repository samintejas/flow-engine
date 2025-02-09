package in.samintejas.v4.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@ToString
public class ExecutionContext {

    private final Map<String, String> context;
    private final Map<String, String> response;

    private ExecutionContext() {
        this.context = new HashMap<>();
        this.response = new HashMap<>();
    }

    private ExecutionContext(ExecutionContext executionContext) {
        this.context = executionContext.getContext();
        this.response = executionContext.getResponse();
    }

    public static ExecutionContext empty(){
        return new ExecutionContext();
    }

    public static ExecutionContext from(ExecutionContext executionContext) {
        return new ExecutionContext(executionContext);
    }
}
