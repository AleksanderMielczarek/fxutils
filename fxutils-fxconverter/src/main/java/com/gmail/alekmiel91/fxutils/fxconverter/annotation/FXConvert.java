package com.gmail.alekmiel91.fxutils.fxconverter.annotation;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
public @interface FXConvert {
    Config[] value() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Config {
        Class<?> value();

        String[] getterSetterNames() default {};

        Element[] getterSetterElements() default {};

        Class<?>[] setterType() default {};
    }
}
