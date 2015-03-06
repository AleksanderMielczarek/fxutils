package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.exception.FXBindingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
public class BinderBidirectional implements Binder {
    @Override
    public void bind(Object annotatedProperty, Object secondProperty, FXBinding fxBinding) {
        Class<?> annotatedPropertyClass = annotatedProperty.getClass();

        try {
            Method bind = getBindMethodFromClass(annotatedPropertyClass, annotatedPropertyClass);
            bind.invoke(annotatedProperty, secondProperty);
        } catch (InvocationTargetException e) {
            throw new FXBindingException("Cannot invoke method named bindBidirectional in " + annotatedPropertyClass.getName() + " for argument " + secondProperty.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new FXBindingException("Cannot access method named bindBidirectional in " + annotatedPropertyClass.getName() + " for argument " + secondProperty.getClass().getName(), e);
        }
    }

    private static Method getBindMethodFromClass(Class<?> aClass, Class<?> superClass) {
        if (superClass.getSuperclass() == null) {
            throw new FXBindingException("No method named bindBidirectional in " + aClass.getName());
        }

        Optional<Method> methodOptional = Arrays.stream(superClass.getDeclaredMethods())
                .filter(method -> method.getName().equals("bindBidirectional"))
                .filter(method -> method.getParameterTypes().length == 1)
                .findFirst();

        if (methodOptional.isPresent()) {
            return methodOptional.get();
        }

        return getBindMethodFromClass(aClass, superClass.getSuperclass());
    }
}
