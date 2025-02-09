package in.samintejas.v4.core.exceptions;

import lombok.Getter;

@Getter
public class FlowValidationException extends RuntimeException{

    /**
     * -- GETTER --
     *  Gets the name of the flow where validation failed.
     *
     * @return the flow name, or null if not specified
     */
    private final String flowName;
    /**
     * -- GETTER --
     *  Gets the ID of the node where validation failed.
     *
     * @return the node ID, or null if not specified
     */
    private final String nodeId;

    /**
     * Constructs a new FlowValidationException with the specified message.
     *
     * @param message the detail message
     */
    public FlowValidationException(String message) {
        super(message);
        this.flowName = null;
        this.nodeId = null;
    }

    /**
     * Constructs a new FlowValidationException with the specified message and flow details.
     *
     * @param message the detail message
     * @param flowName the name of the flow where validation failed
     * @param nodeId the ID of the node where validation failed (if applicable)
     */
    public FlowValidationException(String message, String flowName, String nodeId) {
        super(message);
        this.flowName = flowName;
        this.nodeId = nodeId;
    }

    /**
     * Constructs a new FlowValidationException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the validation failure
     */
    public FlowValidationException(String message, Throwable cause) {
        super(message, cause);
        this.flowName = null;
        this.nodeId = null;
    }

    /**
     * Constructs a new FlowValidationException with the specified message, cause, and flow details.
     *
     * @param message the detail message
     * @param cause the cause of the validation failure
     * @param flowName the name of the flow where validation failed
     * @param nodeId the ID of the node where validation failed (if applicable)
     */
    public FlowValidationException(String message, Throwable cause, String flowName, String nodeId) {
        super(message, cause);
        this.flowName = flowName;
        this.nodeId = nodeId;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(super.getMessage());
        if (flowName != null) {
            message.append(" [Flow: ").append(flowName);
            if (nodeId != null) {
                message.append(", Node: ").append(nodeId);
            }
            message.append("]");
        }
        return message.toString();
    }

}
