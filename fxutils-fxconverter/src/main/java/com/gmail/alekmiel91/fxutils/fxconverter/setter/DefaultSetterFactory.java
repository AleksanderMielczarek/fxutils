package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import com.gmail.alekmiel91.fxutils.fxconverter.types.SetterType;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public class DefaultSetterFactory implements SetterFactory {

    @Override
    public Setter createSetter(Element element, SetterType setterType) {
        Setter setter;

        switch (element) {
            case FIELD:
                setter = new FieldSetter();
                break;
            case METHOD:
                setter = new MethodSetter();
                break;
            default:
                throw new IllegalArgumentException();
        }

        switch (setterType) {
            case MODEL:
                return new ModelSetter(setter);
            case DATA:
                return setter;
            default:
                throw new IllegalArgumentException();
        }
    }
}
