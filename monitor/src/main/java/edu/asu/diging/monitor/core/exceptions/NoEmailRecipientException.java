package edu.asu.diging.monitor.core.exceptions;

public class NoEmailRecipientException extends Exception {

    private static final long serialVersionUID = -3658335972369001233L;

    public NoEmailRecipientException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public NoEmailRecipientException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public NoEmailRecipientException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public NoEmailRecipientException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public NoEmailRecipientException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
