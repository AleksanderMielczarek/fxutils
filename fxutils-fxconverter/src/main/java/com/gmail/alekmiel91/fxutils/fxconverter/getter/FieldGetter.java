package com.gmail.alekmiel91.fxutils.fxconverter.getter;

import com.gmail.alekmiel91.fxutils.fxconverter.exception.ConverterException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class FieldGetter implements Getter {
    @Override
    public <T> Object get(T object, String name) {
        try {
            return PropertyUtils.getProperty(object, name);
        } catch (IllegalAccessException e) {
            throw new ConverterException("Cannot access field " + name + " in " + object.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new ConverterException("Cannot invoke method to get field " + name + " in " + object.getClass().getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ConverterException("No method to get field " + name + " in " + object.getClass().getName(), e);
        }
    }
}
