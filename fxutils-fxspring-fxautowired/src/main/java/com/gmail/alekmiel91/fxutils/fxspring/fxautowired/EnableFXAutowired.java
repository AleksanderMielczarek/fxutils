package com.gmail.alekmiel91.fxutils.fxspring.fxautowired;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FXAutowiredConfig.class)
public @interface EnableFXAutowired {
}
