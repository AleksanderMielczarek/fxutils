package com.gmail.alekmiel91.fxutils.fxconverter.getter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public class DefaultGetterFactory implements GetterFactory {
    @Override
    public Getter createGetter(Element element) {
        switch (element) {
            case FIELD:
                return new FieldGetter();
            case METHOD:
                return new MethodGetter();
            default:
                throw new IllegalArgumentException();
        }
    }
}
