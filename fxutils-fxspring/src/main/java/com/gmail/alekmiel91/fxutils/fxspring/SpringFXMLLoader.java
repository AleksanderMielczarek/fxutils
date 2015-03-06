package com.gmail.alekmiel91.fxutils.fxspring;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-01
 */
public class SpringFXMLLoader {
    private final FXMLLoader fxmlLoader;

    private SpringFXMLLoader(SpringFXMLLoaderBuilder builder) {
        this.fxmlLoader = builder.fxmlLoader;
        builder.fxSpringComponents.forEach(FXSpringComponent::init);
        this.fxmlLoader.setControllerFactory(builder.applicationContext::getBean);
    }

    public <T> T load() throws IOException {
        return fxmlLoader.load();
    }

    public <T> T load(InputStream inputStream) throws IOException {
        return fxmlLoader.load(inputStream);
    }

    public static class SpringFXMLLoaderBuilder {
        private final FXMLLoader fxmlLoader;
        private final ApplicationContext applicationContext;
        private final List<FXSpringComponent> fxSpringComponents = new ArrayList<>();

        public SpringFXMLLoaderBuilder(FXMLLoader fxmlLoader, ApplicationContext applicationContext) {
            this.fxmlLoader = fxmlLoader;
            this.applicationContext = applicationContext;
        }

        public SpringFXMLLoaderBuilder withFXSpringComponent(FXSpringComponent fxSpringComponent) {
            fxSpringComponents.add(fxSpringComponent);
            return this;
        }

        public SpringFXMLLoader build() {
            return new SpringFXMLLoader(this);
        }
    }
}
