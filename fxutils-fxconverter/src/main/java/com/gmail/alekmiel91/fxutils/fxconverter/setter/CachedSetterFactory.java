package com.gmail.alekmiel91.fxutils.fxconverter.setter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import com.gmail.alekmiel91.fxutils.fxconverter.types.SetterType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class CachedSetterFactory implements SetterFactory {
    private static final Table<Element, SetterType, Setter> SETTER_MAP;

    static {
        SETTER_MAP = HashBasedTable.create(Element.values().length, SetterType.values().length);
        SETTER_MAP.put(Element.FIELD, SetterType.DATA, new FieldSetter());
        SETTER_MAP.put(Element.FIELD, SetterType.MODEL, new ModelSetter(SETTER_MAP.get(Element.FIELD, SetterType.DATA)));
        SETTER_MAP.put(Element.METHOD, SetterType.DATA, new MethodSetter());
        SETTER_MAP.put(Element.METHOD, SetterType.MODEL, new ModelSetter(SETTER_MAP.get(Element.METHOD, SetterType.DATA)));
    }

    @Override
    public Setter createSetter(Element element, SetterType setterType) {
        return SETTER_MAP.get(element, setterType);
    }
}
