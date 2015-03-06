package com.gmail.alekmiel91.fxutils.fxspring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface FXController {
    String value() default "";
}
