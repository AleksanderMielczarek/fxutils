package com.gmail.alekmiel91.fxutils.fxspring.fxdefault;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
public class FXDefaultException extends RuntimeException {
    public FXDefaultException(String message) {
        super(message);
    }

    public FXDefaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
