package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
public class BinderTo implements Binder {
    private final Binder binder = new BinderFrom();

    @Override
    public void bind(Object annotatedProperty, Object secondProperty, FXBinding fxBinding) {
        binder.bind(secondProperty, annotatedProperty, fxBinding);
    }
}
