package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.exception.FXBindingException;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import javafx.util.StringConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
public class BinderStringConverter implements Binder {
    @Override
    public void bind(Object annotatedProperty, Object secondProperty, FXBinding fxBinding) {
        Class<?> annotatedPropertyClass = annotatedProperty.getClass();

        try {
            Method bind = getBindMethodFromClass(annotatedPropertyClass, annotatedPropertyClass);
            Object stringConverter = fxBinding.stringConverter()[0].newInstance();
            bind.invoke(annotatedProperty, secondProperty, stringConverter);
        } catch (InvocationTargetException e) {
            throw new FXBindingException("Cannot invoke method named bind in " + annotatedPropertyClass.getName() + " for argument " + secondProperty.getClass().getName() + " and " + fxBinding.stringConverter()[0].getName(), e);
        } catch (IllegalAccessException e) {
            throw new FXBindingException("Cannot access method named bind in " + annotatedPropertyClass.getName() + " for argument " + secondProperty.getClass().getName() + " and " + fxBinding.stringConverter()[0].getName(), e);
        } catch (InstantiationException e) {
            throw new FXBindingException("Cannot create instance of " + fxBinding.stringConverter()[0].getName(), e);
        }
    }

    private static Method getBindMethodFromClass(Class<?> aClass, Class<?> superClass) {
        if (superClass.getSuperclass() == null) {
            throw new FXBindingException("No method named bindBidirectional in " + aClass.getName());
        }

        Optional<Method> methodOptional = Arrays.stream(superClass.getDeclaredMethods())
                .filter(method -> method.getName().equals("bindBidirectional"))
                .filter(method -> method.getParameterTypes().length == 2)
                .filter(method -> method.getParameterTypes()[1].isAssignableFrom(StringConverter.class))
                .findFirst();

        if (methodOptional.isPresent()) {
            return methodOptional.get();
        }

        return getBindMethodFromClass(aClass, superClass.getSuperclass());
    }
}
