package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class GetterSetterElement {
    private final Element getterElement;
    private final Element setterElement;

    public GetterSetterElement(Element getterElement, Element setterElement) {
        this.getterElement = getterElement;
        this.setterElement = setterElement;
    }

    public Element getGetterElement() {
        return getterElement;
    }

    public Element getSetterElement() {
        return setterElement;
    }
}
