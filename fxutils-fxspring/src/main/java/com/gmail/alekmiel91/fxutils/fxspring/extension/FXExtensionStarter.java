package com.gmail.alekmiel91.fxutils.fxspring.extension;

import org.aspectj.lang.JoinPoint;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
public interface FXExtensionStarter {
    public default void start(JoinPoint joinPoint) {
        Object controller = joinPoint.getArgs()[0];
        start(controller);
    }

    public void start(Object controller);
}
