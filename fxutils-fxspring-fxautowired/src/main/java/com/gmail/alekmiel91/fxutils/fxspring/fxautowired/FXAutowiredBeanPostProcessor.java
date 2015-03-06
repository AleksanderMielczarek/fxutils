package com.gmail.alekmiel91.fxutils.fxspring.fxautowired;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
@Component
public class FXAutowiredBeanPostProcessor implements BeanPostProcessor {
    private static Map<String, Object> fieldsMap;

    public static void setFieldsMap(Map<String, Object> fieldsMap) {
        FXAutowiredBeanPostProcessor.fieldsMap = fieldsMap;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        if (!FXAutowiredSpringComponent.AVAILABLE_ANNOTATIONS.stream().anyMatch(beanClass::isAnnotationPresent)) {
            return bean;
        }

        List<Field> fields = Arrays.stream(beanClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(FXAutowired.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            String fieldName = field.getName();

            if (!fieldsMap.containsKey(fieldName)) {
                throw new FXAutowiredException("Cannot inject " + fieldName + " in " + beanClass.getName() + ", because value isn't set or field has wrong name");
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                field.set(bean, fieldsMap.get(field.getName()));
            } catch (IllegalAccessException e) {
                throw new FXAutowiredException("Cannot inject " + fieldName + " in " + beanClass.getName(), e);
            }
        }

        return bean;
    }
}
