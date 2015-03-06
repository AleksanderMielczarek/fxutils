package com.gmail.alekmiel91.fxutils.fxconverter.setter;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public interface Setter {
    public <T, S> void set(T object, S value, String name, Class<?> valueClass);
}
