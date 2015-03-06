package com.gmail.alekmiel91.fxutils.fxspring.fxbinding.utils;

import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.annotation.FXBinding;
import com.gmail.alekmiel91.fxutils.fxspring.fxbinding.type.Bidirectional;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-03
 */
@Component
public class BidirectionalFactory {
    public Bidirectional createBidirectional(FXBinding fxBinding) {
        if (ArrayUtils.isNotEmpty(fxBinding.stringConverter())) {
            return Bidirectional.STRING;
        }
        if (ArrayUtils.isNotEmpty(fxBinding.format())) {
            return Bidirectional.FORMAT;
        }
        return Bidirectional.NONE;
    }
}
