package com.gmail.alekmiel91.fxutils.fxconverter.annotation;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FXConverts {
    FXConvert[] value();
}
