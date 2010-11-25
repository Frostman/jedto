package ru.frostman.jedto.annotations;

import ru.frostman.jedto.transformation.Transformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author slukjanov aka Frostman
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Transform {
    Class<? extends Transformer> value();
}
