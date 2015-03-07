package com.gmail.alekmiel91.fxutils.fxconverter.annotation;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Access;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(FXConverts.class)
@Documented
public @interface FXConvert {
    Class<?>[] value() default {};

    String[] getterSetterNames() default {};

    Element[] getterSetterElements() default {Element.FIELD, Element.FIELD};

    Class<?>[] setterType() default {};

    Access access() default Access.INCLUDE;
}
