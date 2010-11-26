package ru.frostman.jedto.transformation;

/**
 * @author slukjanov aka Frostman
 */
public interface Transformer {
    public Object transformDirect(Object object);

    public Object transformInverse(Object object);
}
