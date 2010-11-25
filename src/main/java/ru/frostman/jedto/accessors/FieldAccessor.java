package ru.frostman.jedto.accessors;

/**
 * @author slukjanov aka Frostman
 */
public interface FieldAccessor {
    public Object getValue(Object instance);

    public void setValue(Object instance, Object newValue);

    public Class getType();
}
