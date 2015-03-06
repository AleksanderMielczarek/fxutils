package com.gmail.alekmiel91.fxutils.fxspring.fxdefault;

import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Configuration
@ComponentScan
@Import(FXExtensionConfig.class)
public class FXDefaultConfig {
}
