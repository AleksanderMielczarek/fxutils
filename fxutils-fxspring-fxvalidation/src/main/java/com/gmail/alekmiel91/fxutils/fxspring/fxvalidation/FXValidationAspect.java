package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation;

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
 * @since 2015-03-04
 */
@Aspect
@Component
@Order(1)
public class FXValidationAspect implements FXExtensionAspect {

    @Autowired
    @Qualifier("FXValidationExtensionStarter")
    private FXExtensionStarter fxExtensionStarter;

    @Before("startFXInitialize()")
    public void createValidation(JoinPoint joinPoint) {
        fxExtensionStarter.start(joinPoint);
    }
}
