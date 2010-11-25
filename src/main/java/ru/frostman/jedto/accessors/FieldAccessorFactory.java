package ru.frostman.jedto.accessors;

import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Field;

/**
 * @author slukjanov aka Frostman
 */
public class FieldAccessorFactory {
    public static FieldAccessor create(Field field, FastClass fastClass) {
        return new SimpleFieldAccessor(field, fastClass);
    }
}
