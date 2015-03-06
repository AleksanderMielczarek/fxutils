package com.gmail.alekmiel91.fxutils.fxconverter.utils;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class GetterSetterName {
    private final String getterName;
    private final String setterName;

    public GetterSetterName(String getterName, String setterName) {
        this.getterName = getterName;
        this.setterName = setterName;
    }

    public String getGetterName() {
        return getterName;
    }

    public String getSetterName() {
        return setterName;
    }
}
