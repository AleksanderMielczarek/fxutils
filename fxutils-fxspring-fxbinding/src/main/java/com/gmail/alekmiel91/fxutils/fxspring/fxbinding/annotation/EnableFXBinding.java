package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.FXBindingConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FXBindingConfig.class)
public @interface EnableFXBinding {
}
