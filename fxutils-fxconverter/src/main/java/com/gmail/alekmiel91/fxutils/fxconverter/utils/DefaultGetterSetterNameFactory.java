package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class DefaultGetterSetterNameFactory implements GetterSetterNameFactory {
    @Override
    public GetterSetterName createGetterSetterName(Optional<FXConvert.Config> configOptional, Field field) {
        String name = field.getName();

        if (!configOptional.isPresent()) {
            return new GetterSetterName(name, name);
        }

        FXConvert.Config config = configOptional.get();
        String[] names = config.getterSetterNames();

        switch (names.length) {
            case 0:
                return new GetterSetterName(name, name);
            case 1:
                return new GetterSetterName(names[0], names[0]);
            default:
                return new GetterSetterName(names[0], names[1]);
        }
    }
}
