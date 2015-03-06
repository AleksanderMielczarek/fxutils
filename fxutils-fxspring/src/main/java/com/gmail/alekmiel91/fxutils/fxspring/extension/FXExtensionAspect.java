package com.gmail.alekmiel91.fxutils.fxspring.extension;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
public interface FXExtensionAspect {
    @Pointcut("execution(public void com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtension.startFXInitialize(..))")
    public default void startFXInitialize() {
    }

    @Pointcut("execution(public void com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtension.finishFXInitialize(..))")
    public default void finishFXInitialize() {
    }
}
