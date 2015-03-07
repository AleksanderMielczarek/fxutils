package com.gmail.alekmiel91.fxutils.fxconverter.exception;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public class FXConverterException extends RuntimeException {
    public FXConverterException(String message) {
        super(message);
    }

    public FXConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
