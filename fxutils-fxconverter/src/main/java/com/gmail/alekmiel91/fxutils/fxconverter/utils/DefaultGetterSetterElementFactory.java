package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class DefaultGetterSetterElementFactory implements GetterSetterElementFactory {
    @Override
    public GetterSetterElement createGetterSetterElement(Optional<FXConvert.Config> configOptional) {
        if (!configOptional.isPresent()) {
            return new GetterSetterElement(Element.FIELD, Element.FIELD);
        }

        FXConvert.Config config = configOptional.get();
        Element[] elements = config.getterSetterElements();

        switch (elements.length) {
            case 0:
                return new GetterSetterElement(Element.FIELD, Element.FIELD);
            case 1:
                return new GetterSetterElement(elements[0], elements[0]);
            default:
                return new GetterSetterElement(elements[0], elements[1]);
        }
    }
}
