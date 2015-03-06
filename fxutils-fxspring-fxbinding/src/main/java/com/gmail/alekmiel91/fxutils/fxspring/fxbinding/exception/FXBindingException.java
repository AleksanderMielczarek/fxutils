package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.exception;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
public class FXBindingException extends RuntimeException {
    public FXBindingException(String message) {
        super(message);
    }

    public FXBindingException(String message, Throwable cause) {
        super(message, cause);
    }
}
