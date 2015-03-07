package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxconverter.exception.FXConverterException;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class FieldSetter implements Setter {
    @Override
    public <T, S> void set(T object, S value, String name, Class<?> valueClass) {
        try {
            BeanUtils.setProperty(object, name, value);
        } catch (IllegalAccessException e) {
            throw new FXConverterException("Cannot access field " + name + " in " + object.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new FXConverterException("Cannot invoke method to access field" + name + " in " + object.getClass().getName(), e);
        }
    }
}
