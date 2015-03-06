package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation.annotation;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-04
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FXValidation {
    String model() default "";

    String property() default "";

    boolean required() default true;

    String value() default "";
}
