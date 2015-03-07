package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface SetterTypeFactory {
    public Class<?> createSetterType(FXConvert fxConvert, Object valueToSet);
}
