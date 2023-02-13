package edu.asu.diging.monitor.core.exceptions;

public class NoEmailRecipientException extends Exception {

    private static final long serialVersionUID = -3658335972369001233L;

    public NoEmailRecipientException() {
        super();
    }

    public NoEmailRecipientException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoEmailRecipientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEmailRecipientException(String message) {
        super(message);
    }

    public NoEmailRecipientException(Throwable cause) {
        super(cause);
    }
}
