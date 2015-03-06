package com.gmail.alekmiel91.fxutils.fxconverter.getter;

import com.gmail.alekmiel91.fxutils.fxconverter.exception.ConverterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class MethodGetter implements Getter {
    @Override
    public <T> Object get(T object, String name) {
        Class<?> objectClass = object.getClass();

        try {
            Method method = objectClass.getMethod(name);
            return method.invoke(object);
        } catch (NoSuchMethodException e) {
            throw new ConverterException("No method " + name + " in " + object.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new ConverterException("Cannot invoke method " + name + " in " + object.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new ConverterException("Cannot access method " + name + " in " + object.getClass().getName(), e);
        }
    }
}
