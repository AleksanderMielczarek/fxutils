package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.utils;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.exception.FXBindingException;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
public class BindingUtils {
    private BindingUtils() {
    }

    public static Field getFieldFromObject(Object object, String field) {
        try {
            return object.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            throw new FXBindingException("No field named " + field + " in " + object.getClass().getName(), e);
        }
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new FXBindingException("No field named " + field + " in " + object.getClass().getName(), e);
        }
    }

    public static String createPropertyMethodName(String property) {
        return property + "Property";
    }

    public static Method getPropertyMethodFromClass(Class<?> aClass, Class<?> superClass, String propertyMethod) {
        if (superClass.getSuperclass() == null) {
            throw new FXBindingException("No property method named " + propertyMethod + " in " + aClass.getName());
        }

        try {
            return superClass.getDeclaredMethod(propertyMethod);
        } catch (NoSuchMethodException e) {
            return getPropertyMethodFromClass(aClass, superClass.getSuperclass(), propertyMethod);
        }
    }

    public static Object invokePropertyMethodOnObject(Object object, Method propertyMethod) {
        try {
            return propertyMethod.invoke(object);
        } catch (IllegalAccessException e) {
            throw new FXBindingException("Cannot access property method named " + propertyMethod.getName() + " on " + object.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new FXBindingException("Cannot invoke property method named " + propertyMethod.getName() + " on " + object.getClass().getName(), e);
        }
    }

    public static void validate(FXBinding fxBinding, Class<?> controllerCLass, Field field) {
        if (ArrayUtils.isNotEmpty(fxBinding.stringConverter()) && ArrayUtils.isNotEmpty(fxBinding.format())) {
            throw new FXBindingException("Cannot determine which binder apply on field named " + field.getName() + " in " + controllerCLass.getName() + " due to both are declared");
        }
    }
}
