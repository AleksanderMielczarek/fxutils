package com.gmail.alekmiel91.fxutils.fxconverter;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class FXConverters {
    private FXConverters() {

    }

    private static final FXConverter FX_CONVERTER = new DefaultFXConverter();

    public static FXConverter getFxConverter() {
        return FX_CONVERTER;
    }
}
