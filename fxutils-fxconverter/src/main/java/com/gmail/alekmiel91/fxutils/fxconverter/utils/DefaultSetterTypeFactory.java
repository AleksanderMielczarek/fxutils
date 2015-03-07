package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class DefaultSetterTypeFactory implements SetterTypeFactory {
    @Override
    public Class<?> createSetterType(FXConvert fxConvert, Object valueToSet) {
        Class<?>[] setterType = fxConvert.setterType();

        if (ArrayUtils.isEmpty(setterType)) {
            return valueToSet.getClass();
        }

        return setterType[0];
    }
}
