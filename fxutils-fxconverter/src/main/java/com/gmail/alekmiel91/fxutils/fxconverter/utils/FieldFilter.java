package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class FieldFilter implements BiPredicate<Field, Class<?>> {
    @Override
    public boolean test(Field field, Class<?> aClass) {
        if (!field.isAnnotationPresent(FXConvert.class)) {
            return false;
        }

        FXConvert convert = field.getAnnotation(FXConvert.class);
        FXConvert.Config[] configs = convert.value();

        if (ArrayUtils.isEmpty(configs)) {
            return true;
        }

        return Arrays.stream(configs)
                .map(FXConvert.Config::value)
                .anyMatch(clazz -> clazz.isAssignableFrom(aClass));
    }
}
