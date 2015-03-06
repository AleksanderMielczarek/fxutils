package com.gmail.alekmiel91.fxutils.fxspring.fxdefault;

import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXController;
import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionStarter;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Direction;
import com.gmail.alekmiel91.fxutils.fxspring.fxdefault.annotation.FXDefault;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Component("FXDefaultExtensionStarter")
public class FXDefaultExtensionStarter implements FXExtensionStarter {

    @Override
    public void start(Object controller) {
        Class<?> controllerClass = controller.getClass();

        if (!controllerClass.isAnnotationPresent(FXController.class)) {
            throw new FXDefaultException("Class " + controllerClass.getName() + " isn't proper FXController due to lack of annotation " + FXController.class.getName());
        }

        List<Field> fields = Arrays.stream(controllerClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FXDefault.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            FXDefault fxDefault = field.getAnnotation(FXDefault.class);
            Optional<FXBinding> fxBindingOptional = getFXBindingAnnotation(field);

            ReflectionUtils.makeAccessible(field);
            Object control = (ReflectionUtils.getField(field, controller));

            String property = fxDefault.property();
            if (property.equals(StringUtils.EMPTY)) {
                property = fxBindingOptional.orElseThrow(() -> new FXDefaultException("No property name for control " + field.getClass().getName() + " named " + field.getName())).property();
                if (property.equals(StringUtils.EMPTY)) {
                    throw new FXDefaultException("No property name for control " + field.getClass().getName() + " named " + field.getName());
                }
            }

            Object value = getDefaultValue(fxDefault, field);

            try {
                BeanUtils.setProperty(control, property, value);
            } catch (IllegalAccessException e) {
                throw new FXDefaultException("Cannot access setter of property named " + property + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            } catch (InvocationTargetException e) {
                throw new FXDefaultException("Cannot invoke setter of property named " + property + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            }
        }
    }

    private static Optional<FXBinding> getFXBindingAnnotation(Field field) {
        List<FXBinding> fxBindings = Arrays.stream(field.getAnnotationsByType(FXBinding.class))
                .filter(binding -> binding.direction().equals(Direction.SOURCE) ||
                        binding.direction().equals(Direction.BIDIRECTIONAL) ||
                        ArrayUtils.isNotEmpty(binding.stringConverter()) ||
                        ArrayUtils.isNotEmpty(binding.format()))
                .collect(Collectors.toList());

        if (fxBindings.isEmpty()) {
            return Optional.empty();
        }

        if (fxBindings.size() > 1) {
            return Optional.empty();
        }

        return Optional.of(fxBindings.get(0));
    }

    private static Object getDefaultValue(FXDefault fxDefault, Field field) {
        if (ArrayUtils.isNotEmpty(fxDefault.defaultByte())) {
            return fxDefault.defaultByte()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultShort())) {
            return fxDefault.defaultShort()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultInt())) {
            return fxDefault.defaultInt()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultLong())) {
            return fxDefault.defaultLong()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultFloat())) {
            return fxDefault.defaultFloat()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultDouble())) {
            return fxDefault.defaultDouble()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultBoolean())) {
            return fxDefault.defaultBoolean()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultChar())) {
            return fxDefault.defaultChar()[0];
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultClass())) {
            Class<?> defaultClass = fxDefault.defaultClass()[0];
            try {
                return defaultClass.newInstance();
            } catch (InstantiationException e) {
                throw new FXDefaultException("Cannot create instance of " + defaultClass.getName() + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            } catch (IllegalAccessException e) {
                throw new FXDefaultException("Cannot access constructor of " + defaultClass.getName() + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            }
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultSupplier())) {
            Class<? extends Supplier<?>> supplierClass = fxDefault.defaultSupplier()[0];
            try {
                Supplier<?> supplier = supplierClass.newInstance();
                return supplier.get();
            } catch (InstantiationException e) {
                throw new FXDefaultException("Cannot create instance of " + supplierClass.getName() + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            } catch (IllegalAccessException e) {
                throw new FXDefaultException("Cannot access constructor of " + supplierClass.getName() + " for control " + field.getClass().getName() + " named " + field.getName(), e);
            }
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultEnum()) && ArrayUtils.isNotEmpty(fxDefault.defaultString())) {
            String name = fxDefault.defaultString()[0];
            Enum<?>[] enums = fxDefault.defaultEnum()[0].getEnumConstants();
            return Arrays.stream(enums)
                    .filter(enumm -> enumm.toString().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new FXDefaultException("No enum for name " + name + "for control " + field.getClass().getName() + " named " + field.getName()));
        }

        if (ArrayUtils.isNotEmpty(fxDefault.defaultString())) {
            return fxDefault.defaultString()[0];
        }

        throw new FXDefaultException("No default value has been set for control " + field.getClass().getName() + " named " + field.getName());
    }
}
