package in.samintejas.v4.core.exceptions;

public class RequestGenerationException extends RuntimeException {
    public RequestGenerationException(String message) {
        super(message);
    }
    public RequestGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
