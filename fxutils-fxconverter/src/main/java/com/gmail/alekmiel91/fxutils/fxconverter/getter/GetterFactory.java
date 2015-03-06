package com.gmail.alekmiel91.fxutils.fxconverter.getter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface GetterFactory {
    public Getter createGetter(Element element);
}
