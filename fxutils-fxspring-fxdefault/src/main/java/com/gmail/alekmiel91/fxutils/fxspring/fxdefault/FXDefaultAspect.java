package com.gmail.alekmiel91.fxutils.fxspring.fxdefault;

import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionAspect;
import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionStarter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Aspect
@Component
public class FXDefaultAspect implements FXExtensionAspect {

    @Autowired
    @Qualifier("FXDefaultExtensionStarter")
    private FXExtensionStarter fxExtensionStarter;

    @Before("finishFXInitialize()")
    public void createDefault(JoinPoint joinPoint) {
        fxExtensionStarter.start(joinPoint);
    }
}
