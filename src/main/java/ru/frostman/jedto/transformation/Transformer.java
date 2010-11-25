package ru.frostman.jedto.transformation;

/**
 * @author slukjanov aka Frostman
 */
public interface Transformer<T1, T2> {
    public T2 transformFirst(T1 t1);

    public T1 transformSecond(T2 t2);
}
