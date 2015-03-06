package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import com.gmail.alekmiel91.fxutils.fxconverter.types.SetterType;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public interface SetterFactory {
    public Setter createSetter(Element element, SetterType setterType);
}
