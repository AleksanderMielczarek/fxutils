package com.gmail.alekmiel91.fxutils.fxspring.fxupdater;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FXUpdaterConfig.class)
public @interface EnableFXUpdater {
}
