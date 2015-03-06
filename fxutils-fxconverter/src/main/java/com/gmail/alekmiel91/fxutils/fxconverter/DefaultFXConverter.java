package com.gmail.alekmiel91.fxutils.fxconverter;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import com.gmail.alekmiel91.fxutils.fxconverter.exception.ConverterException;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.CachedGetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.Getter;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.GetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.CachedSetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.Setter;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.SetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import com.gmail.alekmiel91.fxutils.fxconverter.types.SetterType;
import com.gmail.alekmiel91.fxutils.fxconverter.utils.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public class DefaultFXConverter implements FXConverter {
    private final FieldFilter fieldFilter = new FieldFilter();
    private final ConfigFilter configFilter = new ConfigFilter();
    private final GetterSetterNameFactory getterSetterNameFactory = new DefaultGetterSetterNameFactory();
    private final GetterSetterElementFactory getterSetterElementFactory = new DefaultGetterSetterElementFactory();
    private final SetterTypeFactory setterTypeFactory = new DefaultSetterTypeFactory();
    private final GetterFactory getterFactory = new CachedGetterFactory();
    private final SetterFactory setterFactory = new CachedSetterFactory();

    @Override
    public <T, S> void convertFromModelToData(T fromModel, S toData) {
        Class<?> fromModelClass = fromModel.getClass();
        Class<?> toDataClass = toData.getClass();
        convertFromModelToData(fromModel, toData, fromModelClass, toDataClass);
    }

    @Override
    public <T, S> S convertFromModelToData(T fromModel, Class<S> toDataClass) {
        Class<?> fromModelClass = fromModel.getClass();

        S toData;

        try {
            toData = toDataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConverterException("Cannot call constructor of " + toDataClass.getName(), e);
        }

        convertFromModelToData(fromModel, toData, fromModelClass, toDataClass);

        return toData;
    }

    @Override
    public <T, S> void convertFromDataToModel(T fromData, S toModel) {
        Class<?> fromDataClass = fromData.getClass();
        Class<?> toModelClass = toModel.getClass();
        convertFromDataToModel(fromData, toModel, fromDataClass, toModelClass);
    }

    @Override
    public <T, S> S convertFromDataToModel(T fromData, Class<S> toModelClass) {
        Class<?> fromDataClass = fromData.getClass();

        S toModel;

        try {
            toModel = toModelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConverterException("Cannot call constructor of " + toModelClass.getName(), e);
        }

        convertFromDataToModel(fromData, toModel, fromDataClass, toModelClass);

        return toModel;
    }

    private <T, S> void convertFromModelToData(T fromModel, S toData, Class<?> fromModelClass, Class<?> toDataClass) {
        Field[] fields = fromModelClass.getDeclaredFields();

        Arrays.stream(fields)
                .filter(field -> fieldFilter.test(field, toDataClass))
                .forEach(field -> {
                    String modelName = field.getName();
                    FXConvert convert = field.getAnnotation(FXConvert.class);
                    FXConvert.Config[] configs = convert.value();
                    Optional<FXConvert.Config> configOptional = configFilter.filter(configs, toDataClass);

                    GetterSetterName getterSetterName = getterSetterNameFactory.createGetterSetterName(configOptional, field);
                    GetterSetterElement getterSetterElement = getterSetterElementFactory.createGetterSetterElement(configOptional);
                    Getter getter = getterFactory.createGetter(Element.FIELD);
                    Setter setter = setterFactory.createSetter(getterSetterElement.getSetterElement(), SetterType.DATA);
                    Object value = getter.get(fromModel, modelName);
                    setter.set(toData, value, getterSetterName.getSetterName(), setterTypeFactory.createSetterType(configOptional, value));
                });
    }

    private <T, S> void convertFromDataToModel(T fromData, S toModel, Class<?> fromDataClass, Class<?> toModelClass) {
        Field[] fields = toModelClass.getDeclaredFields();

        Arrays.stream(fields)
                .filter(field -> fieldFilter.test(field, fromDataClass))
                .forEach(field -> {
                    String modelName = field.getName();
                    FXConvert convert = field.getAnnotation(FXConvert.class);
                    FXConvert.Config[] configs = convert.value();
                    Optional<FXConvert.Config> configOptional = configFilter.filter(configs, fromDataClass);

                    GetterSetterName getterSetterName = getterSetterNameFactory.createGetterSetterName(configOptional, field);
                    GetterSetterElement getterSetterElement = getterSetterElementFactory.createGetterSetterElement(configOptional);
                    Getter getter = getterFactory.createGetter(getterSetterElement.getGetterElement());
                    Setter setter = setterFactory.createSetter(Element.FIELD, SetterType.MODEL);
                    Object value = getter.get(fromData, getterSetterName.getGetterName());
                    setter.set(toModel, value, modelName, value.getClass());
                });
    }
}
