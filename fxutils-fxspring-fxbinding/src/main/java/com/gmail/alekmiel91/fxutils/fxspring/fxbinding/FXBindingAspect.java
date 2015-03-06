package com.gmail.alekmiel91.fxutils.fxspring.fxbinding;

import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionAspect;
import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionStarter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-02
 */
@Aspect
@Component
@Order(2)
public class FXBindingAspect implements FXExtensionAspect {

    @Autowired
    @Qualifier("FXBinderExtensionStarter")
    private FXExtensionStarter fxExtensionStarter;

    @Before("startFXInitialize()")
    public void createBindings(JoinPoint joinPoint) {
        fxExtensionStarter.start(joinPoint);
    }
}
