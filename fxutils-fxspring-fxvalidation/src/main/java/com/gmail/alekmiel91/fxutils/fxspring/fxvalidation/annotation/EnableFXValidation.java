package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation.annotation;

import com.gmail.alekmiel91.fxutils.fxspring.fxvalidation.FXValidationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FXValidationConfig.class)
public @interface EnableFXValidation {
}
