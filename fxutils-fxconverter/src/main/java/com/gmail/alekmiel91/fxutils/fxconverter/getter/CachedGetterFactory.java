package com.gmail.alekmiel91.fxutils.fxconverter.getter;

import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class CachedGetterFactory implements GetterFactory {
    private static final Map<Element, Getter> GETTER_MAP;

    static {
        GETTER_MAP = new EnumMap<>(Element.class);
        GETTER_MAP.put(Element.FIELD, new FieldGetter());
        GETTER_MAP.put(Element.METHOD, new MethodGetter());
    }

    @Override
    public Getter createGetter(Element element) {
        return GETTER_MAP.get(element);
    }
}
