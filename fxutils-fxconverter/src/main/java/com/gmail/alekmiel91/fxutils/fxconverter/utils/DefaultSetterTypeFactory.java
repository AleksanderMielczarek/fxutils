package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class DefaultSetterTypeFactory implements SetterTypeFactory {
    @Override
    public Class<?> createSetterType(Optional<FXConvert.Config> configOptional, Object valueToSet) {
        if (!configOptional.isPresent()) {
            return valueToSet.getClass();
        }

        FXConvert.Config config = configOptional.get();
        Class<?>[] setterType = config.setterType();

        if (ArrayUtils.isEmpty(setterType)) {
            return valueToSet.getClass();
        }

        return setterType[0];
    }
}
