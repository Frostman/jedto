package ru.frostman.jedto.util;

import ru.frostman.jedto.accessors.FieldAccessor;
import ru.frostman.jedto.transformation.Transformer;

/**
 * @author slukjanov aka Frostman
 */
public class FieldConverter {
    private final FieldAccessor mainField;
    private final FieldAccessor dtoField;
    private final Transformer transformer;

    public FieldConverter(FieldAccessor mainField, FieldAccessor dtoField, Transformer transformer) {
        if (mainField == null || dtoField == null) {
            throw new IllegalArgumentException("First two arguments can't be null");
        }

        if (transformer != null) {
            //todo check types
        }

        this.mainField = mainField;
        this.dtoField = dtoField;
        this.transformer = transformer;
    }

    public FieldAccessor getMainField() {
        return mainField;
    }

    public FieldAccessor getDtoField() {
        return dtoField;
    }

    public Transformer getTransformer() {
        return transformer;
    }
}
