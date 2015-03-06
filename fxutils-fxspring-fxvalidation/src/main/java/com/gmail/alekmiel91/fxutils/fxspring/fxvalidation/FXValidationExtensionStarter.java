package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation;


import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXController;
import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionStarter;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Direction;
import com.gmail.alekmiel91.fxutils.fxspring.fxvalidation.annotation.FXValidation;
import javafx.scene.control.Control;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.validation.ValidationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Component("FXValidationExtensionStarter")
public class FXValidationExtensionStarter implements FXExtensionStarter {

    private static final ValidationSupport GLOBAL_VALIDATION_SUPPORT = new ValidationSupport();

    @Autowired
    private Validator validator;

    @Override
    public void start(Object controller) {
        Class<?> controllerClass = controller.getClass();

        if (!controllerClass.isAnnotationPresent(FXController.class)) {
            throw new FXValidationException("Class " + controllerClass.getName() + " isn't proper FXController due to lack of annotation " + FXController.class.getName());
        }

        List<Field> fields = Arrays.stream(controllerClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FXValidation.class))
                .collect(Collectors.toList());

        Map<String, ValidationSupport> validationSupportMap = new HashMap<>();

        for (Field field : fields) {
            FXValidation fxValidation = field.getAnnotation(FXValidation.class);
            Optional<FXBinding> fxBindingOptional = getFXBindingAnnotation(field);

            ReflectionUtils.makeAccessible(field);
            Control control = (Control) ReflectionUtils.getField(field, controller);

            String modelName = fxValidation.model();
            if (modelName.equals(StringUtils.EMPTY)) {
                modelName = fxBindingOptional.orElseThrow(() -> new FXValidationException("No model field name for control " + field.getClass().getName() + " named " + field.getName())).field();
                if (modelName.equals(StringUtils.EMPTY)) {
                    throw new FXValidationException("No model field name for control " + field.getClass().getName() + " named " + field.getName());
                }
            }
            Field modelField = ReflectionUtils.findField(controllerClass, modelName);
            ReflectionUtils.makeAccessible(modelField);
            Object model = ReflectionUtils.getField(modelField, controller);

            String propertyName = fxValidation.property();
            if (propertyName.equals(StringUtils.EMPTY)) {
                propertyName = fxBindingOptional.orElseThrow(() -> new FXValidationException("No property field name for control " + field.getClass().getName() + " named " + field.getName())).propertySecond();
                if (propertyName.equals(StringUtils.EMPTY)) {
                    propertyName = fxBindingOptional.get().property();
                }
                if (propertyName.equals(StringUtils.EMPTY)) {
                    throw new FXValidationException("No property field name for control " + field.getClass().getName() + " named " + field.getName());
                }
            }

            String validationSupportName = fxValidation.value();
            if (validationSupportName.equals(StringUtils.EMPTY)) {
                GLOBAL_VALIDATION_SUPPORT.registerValidator(control, fxValidation.required(), new FXValidator(validator, model, propertyName));
                continue;
            }

            if (validationSupportMap.containsKey(validationSupportName)) {
                validationSupportMap.get(validationSupportName).registerValidator(control, fxValidation.required(), new FXValidator(validator, model, propertyName));
                continue;
            }

            Field validationSupportField = ReflectionUtils.findField(controllerClass, validationSupportName);
            ReflectionUtils.makeAccessible(validationSupportField);
            ValidationSupport validationSupport = (ValidationSupport) ReflectionUtils.getField(validationSupportField, controller);

            validationSupport.registerValidator(control, fxValidation.required(), new FXValidator(validator, model, propertyName));
            validationSupportMap.put(validationSupportName, validationSupport);
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
}
