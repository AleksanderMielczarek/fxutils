package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binderdispatcher;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder.Binder;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.binder.BinderFactory;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.exception.FXBindingException;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.utils.BindingUtils;
import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXController;
import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionStarter;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
@Component("FXBinderExtensionStarter")
public class FXBinderExtensionStarter implements FXExtensionStarter {

    @Autowired
    private BinderFactory binderFactory;

    @Override
    public void start(Object controller) {
        Class<?> controllerClass = controller.getClass();

        if (!controllerClass.isAnnotationPresent(FXController.class)) {
            throw new FXBindingException("No annotation " + FXController.class.getName() + " in " + controllerClass.getName());
        }

        Arrays.stream(controllerClass.getDeclaredFields())
                .forEach(annotatedField -> {
                    Object annotatedObject = BindingUtils.getFieldValue(controller, annotatedField);
                    Arrays.stream(annotatedField.getAnnotationsByType(FXBinding.class))
                            .forEach(fxBinding -> {
                                BindingUtils.validate(fxBinding, controllerClass, annotatedField);

                                String annotatedPropertyMethodName = BindingUtils.createPropertyMethodName(fxBinding.property());
                                Method annotatedPropertyMethod = BindingUtils.getPropertyMethodFromClass(annotatedObject.getClass(), annotatedObject.getClass(), annotatedPropertyMethodName);
                                Object annotatedProperty = BindingUtils.invokePropertyMethodOnObject(annotatedObject, annotatedPropertyMethod);

                                Field secondField = BindingUtils.getFieldFromObject(controller, fxBinding.field());
                                Object secondObject = BindingUtils.getFieldValue(controller, secondField);
                                String secondPropertyMethodName = BindingUtils.createPropertyMethodName(Strings.isNullOrEmpty(fxBinding.propertySecond()) ? annotatedPropertyMethodName : fxBinding.propertySecond());
                                Method secondPropertyMethod = BindingUtils.getPropertyMethodFromClass(secondObject.getClass(), secondObject.getClass(), secondPropertyMethodName);
                                Object secondProperty = BindingUtils.invokePropertyMethodOnObject(secondObject, secondPropertyMethod);

                                Binder binder = binderFactory.createBinder(fxBinding);
                                binder.bind(annotatedProperty, secondProperty, fxBinding);
                            });
                });
    }
}
