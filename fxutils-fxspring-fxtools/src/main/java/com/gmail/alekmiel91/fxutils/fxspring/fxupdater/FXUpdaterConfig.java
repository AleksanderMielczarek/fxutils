package com.gmail.alekmiel91.fxutils.fxspring.fxupdater;

import com.gmail.alekmiel91.fxutils.fxupdater.FXUpdater;
import com.gmail.alekmiel91.fxutils.fxupdater.FXUpdaterUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Configuration
@ComponentScan
public class FXUpdaterConfig {

    @Bean
    public FXUpdater FXUpdater() {
        return FXUpdaterUtils.getFxUpdater();
    }

}
