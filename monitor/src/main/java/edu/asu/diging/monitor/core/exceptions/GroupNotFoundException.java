package edu.asu.diging.monitor.core.exceptions;

public class GroupNotFoundException extends Throwable {

    /**
     * 
     */
    private static final long serialVersionUID = -7690559004095252150L;

    public GroupNotFoundException() {
    }

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
