package com.gmail.alekmiel91.fxutils.fxspring.fxvalidation;

import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.ResourceBundle;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-04
 */
@Configuration
@ComponentScan
@Import(FXExtensionConfig.class)
public class FXValidationConfig {

    @Bean
    public Validator validator(ResourceBundle resources) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resources.getBaseBundleName());
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
