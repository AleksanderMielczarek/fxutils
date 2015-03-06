package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface GetterSetterElementFactory {
    public GetterSetterElement createGetterSetterElement(Optional<FXConvert.Config> configOptional);
}
