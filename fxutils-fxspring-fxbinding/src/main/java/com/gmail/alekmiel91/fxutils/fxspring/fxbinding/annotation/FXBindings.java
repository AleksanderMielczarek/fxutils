package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FXBindings {
    FXBinding[] value();
}
