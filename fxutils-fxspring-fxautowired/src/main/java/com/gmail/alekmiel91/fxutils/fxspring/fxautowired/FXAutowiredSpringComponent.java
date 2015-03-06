package com.gmail.alekmiel91.fxutils.fxspring.fxautowired;

import com.gmail.alekmiel91.fxutils.fxspring.FXSpringComponent;
import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXController;
import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXModel;
import com.gmail.alekmiel91.fxutils.fxspring.annotation.FXService;
import com.google.common.collect.Sets;
import javafx.stage.Stage;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
public class FXAutowiredSpringComponent implements FXSpringComponent {
    private static final String STAGE_NAME = "stage";
    private static final String RESOURCES_NAME = "resources";
    private static final String LOCATION_NAME = "location";

    static Set<Class<? extends Annotation>> AVAILABLE_ANNOTATIONS = Sets.newHashSet(FXController.class, FXModel.class, FXService.class);

    private final Map<String, Object> fieldsMap;

    private FXAutowiredSpringComponent(FXAutowiredSpringComponentBuilder builder) {
        this.fieldsMap = builder.fieldsMap;
    }

    @Override
         public void init() {
        FXAutowiredBeanPostProcessor.setFieldsMap(fieldsMap);
    }

    public static class FXAutowiredSpringComponentBuilder {
        private final Map<String, Object> fieldsMap = new HashMap<>();

        public FXAutowiredSpringComponentBuilder withStage(Stage stage) {
            fieldsMap.put(STAGE_NAME, stage);
            return this;
        }

        public FXAutowiredSpringComponentBuilder withResources(ResourceBundle resources) {
            fieldsMap.put(RESOURCES_NAME, resources);
            return this;
        }

        public FXAutowiredSpringComponentBuilder withLocation(URL location) {
            fieldsMap.put(LOCATION_NAME, location);
            return this;
        }

        public <T> FXAutowiredSpringComponentBuilder withObject(String fieldName, T object) {
            fieldsMap.put(fieldName, object);
            return this;
        }

        public FXAutowiredSpringComponent build() {
            return new FXAutowiredSpringComponent(this);
        }
    }
}
