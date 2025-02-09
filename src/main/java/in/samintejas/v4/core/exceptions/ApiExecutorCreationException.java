package in.samintejas.v4.core.exceptions;

public class ApiExecutorCreationException extends RuntimeException {
    public ApiExecutorCreationException(String message) {
        super(message);
    }

    public ApiExecutorCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
