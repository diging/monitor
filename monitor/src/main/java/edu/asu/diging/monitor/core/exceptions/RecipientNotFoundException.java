package edu.asu.diging.monitor.core.exceptions;

public class RecipientNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public RecipientNotFoundException() {
        super();
    }

    public RecipientNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RecipientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipientNotFoundException(String message) {
        super(message);
    }

    public RecipientNotFoundException(Throwable cause) {
        super(cause);
    }
}
