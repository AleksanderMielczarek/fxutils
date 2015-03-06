package com.gmail.alekmiel91.fxutils.fxspring.fxautowired;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
public class FXAutowiredException extends RuntimeException {
    public FXAutowiredException(String message) {
        super(message);
    }

    public FXAutowiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
