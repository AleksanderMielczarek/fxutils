package com.gmail.alekmiel91.fxutils.fxconverter.getter;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public interface Getter {
    public <T> Object get(T object, String name);
}
