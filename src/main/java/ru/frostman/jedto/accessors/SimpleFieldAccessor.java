package ru.frostman.jedto.accessors;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import ru.frostman.jedto.util.ReflectionUtil;

import java.lang.reflect.Field;

/**
 * @author slukjanov aka Frostman
 */
public class SimpleFieldAccessor implements FieldAccessor {
    private Field field;

    private FastMethod getter;
    private FastMethod setter;

    public SimpleFieldAccessor(Field field) {
        this(field, FastClass.create(field.getDeclaringClass()));
    }

    public SimpleFieldAccessor(Field field, FastClass fastClass) {
        this.field = field;

        if (field == null || fastClass == null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }

        field.setAccessible(true);

        getter = fastClass.getMethod(ReflectionUtil.getFieldGetterName(field), new Class[]{});
        setter = fastClass.getMethod(ReflectionUtil.getFieldSetterName(field), new Class[]{field.getType()});
    }

    public Object getValue(Object instance) {
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            //todo is it good?
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object instance, Object newValue) {
        try {
            field.set(instance, newValue);
        } catch (IllegalAccessException e) {
            //todo impl is is good?
            throw new RuntimeException(e);
        }
    }
}
