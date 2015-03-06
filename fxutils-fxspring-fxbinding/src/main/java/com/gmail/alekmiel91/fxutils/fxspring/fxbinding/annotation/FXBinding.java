package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Direction;
import javafx.util.StringConverter;

import java.lang.annotation.*;
import java.text.Format;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(FXBindings.class)
public @interface FXBinding {
    String field();

    String property();

    String propertySecond() default "";

    Direction direction() default Direction.SOURCE;

    Class<? extends StringConverter<?>>[] stringConverter() default {};

    Class<? extends Format>[] format() default {};
}
