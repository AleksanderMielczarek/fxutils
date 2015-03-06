package com.gmail.alekmiel91.fxutils.fxspring.fxbinding;

import com.gmail.alekmiel91.fxutils.fxspring.extension.FXExtensionConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-02
 */
@Configuration
@ComponentScan
@Import(FXExtensionConfig.class)
public class FXBindingConfig {

}
