package com.gmail.alekmiel91.fxutils.fxspring.fxconverter;

import com.gmail.alekmiel91.fxutils.fxconverter.FXConverter;
import com.gmail.alekmiel91.fxutils.fxconverter.FXConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Configuration
@ComponentScan
public class FXConverterConfig {

    @Bean
    public FXConverter FXConverter() {
        return FXConverters.getFxConverter();
    }

}
