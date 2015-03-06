package com.gmail.alekmiel91.fxutils.fxconverter.utils;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class ConfigFilter {
    public Optional<FXConvert.Config> filter(FXConvert.Config[] configs, Class<?> toDataClass) {
        return Arrays.stream(configs)
                .filter(config -> config.value().isAssignableFrom(toDataClass))
                .findFirst();
    }
}
