package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface SetterTypeFactory {
    public Class<?> createSetterType(Optional<FXConvert.Config> configOptional, Object valueToSet);
}
