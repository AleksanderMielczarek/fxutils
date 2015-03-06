package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxupdater.FXUpdater;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class ModelSetter implements Setter,FXUpdater {
    private final Setter setter;

    public ModelSetter(Setter setter) {
        this.setter = setter;
    }

    @Override
    public <T, S> void set(T object, S value, String name, Class<?> valueClass) {
        update(() -> setter.set(object, value, name, valueClass));
    }
}
