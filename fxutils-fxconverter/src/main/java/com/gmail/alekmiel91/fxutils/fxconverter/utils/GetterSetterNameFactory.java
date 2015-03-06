package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface GetterSetterNameFactory {
    public GetterSetterName createGetterSetterName(Optional<FXConvert.Config> configOptional, Field field);
}
