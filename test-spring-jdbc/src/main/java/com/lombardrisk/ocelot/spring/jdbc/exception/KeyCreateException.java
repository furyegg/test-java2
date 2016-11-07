package com.lombardrisk.ocelot.spring.jdbc.exception;

/**
 * Created by jason zhang on 3/29/2016.
 */
public class KeyCreateException extends Exception {
    public KeyCreateException() {
    }

    public KeyCreateException(String message) {
        super(message);
    }

    public KeyCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyCreateException(Throwable cause) {
        super(cause);
    }

    public KeyCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
