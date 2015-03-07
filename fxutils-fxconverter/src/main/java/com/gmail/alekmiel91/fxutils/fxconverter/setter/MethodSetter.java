package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxconverter.exception.FXConverterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class MethodSetter implements Setter {
    @Override
    public <T, S> void set(T object, S value, String name, Class<?> valueClass) {
        try {
            Class<?> objectClass = object.getClass();
            Method method = objectClass.getDeclaredMethod(name, valueClass);
            method.invoke(object, value);
        } catch (NoSuchMethodException e) {
            throw new FXConverterException("No method " + name + " in " + object.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new FXConverterException("Cannot invoke method " + name + " in " + object.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new FXConverterException("Cannot access method " + name + " in " + object.getClass().getName(), e);
        }
    }
}
