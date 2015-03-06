package com.gmail.alekmiel91.fxutils.fxspring.fxdefault.annotation;

import java.lang.annotation.*;
import java.util.function.Supplier;

/**
 * @author Aleksander Mielczarek
 * @since 2015-03-06
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FXDefault {
    String property() default "";

    byte[] defaultByte() default {};

    short[] defaultShort() default {};

    int[] defaultInt() default {};

    long[] defaultLong() default {};

    float[] defaultFloat() default {};

    double[] defaultDouble() default {};

    boolean[] defaultBoolean() default {};

    char[] defaultChar() default {};

    String[] defaultString() default {};

    Class<? extends Enum<?>>[] defaultEnum() default {};

    Class<?>[] defaultClass() default {};

    Class<? extends Supplier<?>>[] defaultSupplier() default {};
}
