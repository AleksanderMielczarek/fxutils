package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-04
 */
public class FXValidationException extends RuntimeException {
    public FXValidationException(String message) {
        super(message);
    }

    public FXValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
