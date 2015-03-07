package com.gmail.alekmiel91.fxutils.fxconverter;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import com.gmail.alekmiel91.fxutils.fxconverter.exception.FXConverterException;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.CachedGetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.Getter;
import com.gmail.alekmiel91.fxutils.fxconverter.getter.GetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.CachedSetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.Setter;
import com.gmail.alekmiel91.fxutils.fxconverter.setter.SetterFactory;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Access;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import com.gmail.alekmiel91.fxutils.fxconverter.types.SetterType;
import com.gmail.alekmiel91.fxutils.fxconverter.utils.*;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public class DefaultFXConverter implements FXConverter {
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
            throw new FXConverterException("Cannot call constructor of " + toDataClass.getName(), e);
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
            throw new FXConverterException("Cannot call constructor of " + toModelClass.getName(), e);
        }

        convertFromDataToModel(fromData, toModel, fromDataClass, toModelClass);

        return toModel;
    }

    private <T, S> void convertFromModelToData(T fromModel, S toData, Class<?> fromModelClass, Class<?> toDataClass) {
        Field[] fields = fromModelClass.getDeclaredFields();

        for (Field field : fields) {
            FXConvert[] fxConverts = field.getAnnotationsByType(FXConvert.class);

            if (ArrayUtils.isEmpty(fxConverts)) {
                continue;
            }

            Optional<FXConvert> fxConvertOptional = getFXConvert(field, fxConverts, toDataClass, fromModelClass);

            if (!fxConvertOptional.isPresent()) {
                continue;
            }

            String modelName = field.getName();
            FXConvert fxConvert = fxConvertOptional.get();

            GetterSetterName getterSetterName = getterSetterNameFactory.createGetterSetterName(fxConvert, field);
            GetterSetterElement getterSetterElement = getterSetterElementFactory.createGetterSetterElement(fxConvert);
            Getter getter = getterFactory.createGetter(Element.FIELD);
            Setter setter = setterFactory.createSetter(getterSetterElement.getSetterElement(), SetterType.DATA);
            Object value = getter.get(fromModel, modelName);
            setter.set(toData, value, getterSetterName.getSetterName(), setterTypeFactory.createSetterType(fxConvert, value));
        }
    }

    private <T, S> void convertFromDataToModel(T fromData, S toModel, Class<?> fromDataClass, Class<?> toModelClass) {
        Field[] fields = toModelClass.getDeclaredFields();

        for (Field field : fields) {
            FXConvert[] fxConverts = field.getAnnotationsByType(FXConvert.class);

            if (ArrayUtils.isEmpty(fxConverts)) {
                continue;
            }

            Optional<FXConvert> fxConvertOptional = getFXConvert(field, fxConverts, fromDataClass, toModelClass);

            if (!fxConvertOptional.isPresent()) {
                continue;
            }

            String modelName = field.getName();
            FXConvert fxConvert = fxConvertOptional.get();

            GetterSetterName getterSetterName = getterSetterNameFactory.createGetterSetterName(fxConvert, field);
            GetterSetterElement getterSetterElement = getterSetterElementFactory.createGetterSetterElement(fxConvert);
            Getter getter = getterFactory.createGetter(getterSetterElement.getGetterElement());
            Setter setter = setterFactory.createSetter(Element.FIELD, SetterType.MODEL);
            Object value = getter.get(fromData, getterSetterName.getGetterName());
            setter.set(toModel, value, modelName, value.getClass());
        }
    }

    private static Optional<FXConvert> getFXConvert(Field field, FXConvert[] fxConverts, Class<?> dataClass, Class<?> modelClass) {
        if (fxConverts.length == 1) {
            FXConvert fxConvert = fxConverts[0];

            if (ArrayUtils.isEmpty(fxConvert.value())) {
                return fxConvert.access().equals(Access.INCLUDE) ? Optional.of(fxConvert) : Optional.empty();
            }

            if (fxConvert.value()[0].isAssignableFrom(dataClass)) {
                return fxConvert.access().equals(Access.INCLUDE) ? Optional.of(fxConvert) : Optional.empty();
            }

            return Optional.empty();
        }


        //assignable
        List<FXConvert> assignableFXConverts = Arrays.stream(fxConverts)
                .filter(fxConvert -> ArrayUtils.isNotEmpty(fxConvert.value()))
                .filter(fxConvert -> fxConvert.value()[0].isAssignableFrom(dataClass))
                .collect(Collectors.toList());

        if (assignableFXConverts.size() > 1) {
            throw new FXConverterException("Cannot determine which converter choose on field named " + field.getName() + " in " + modelClass.getName());
        }

        if (!assignableFXConverts.isEmpty()) {
            return assignableFXConverts.stream()
                    .filter(fxConvert -> fxConvert.access().equals(Access.INCLUDE))
                    .findFirst();
        }


        //default
        List<FXConvert> defaultFXConverts = Arrays.stream(fxConverts)
                .filter(fxConvert -> ArrayUtils.isEmpty(fxConvert.value()))
                .collect(Collectors.toList());

        if (defaultFXConverts.isEmpty()) {
            return Optional.empty();
        }

        if (defaultFXConverts.size() > 1) {
            throw new FXConverterException("Cannot determine which converter choose on field named " + field.getName() + " in " + modelClass.getName());
        }

        return defaultFXConverts.stream()
                .filter(fxConvert -> fxConvert.access().equals(Access.INCLUDE))
                .findFirst();
    }
}
