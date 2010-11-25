package ru.frostman.jedto.util;

import ru.frostman.jedto.accessors.FieldAccessor;

/**
 * @author slukjanov aka Frostman
 */
public class FieldConverter {
    private final FieldAccessor mainField;
    private final FieldAccessor dtoField;

    public FieldConverter(FieldAccessor mainField, FieldAccessor dtoField) {
        if (mainField == null || dtoField == null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }

        this.mainField = mainField;
        this.dtoField = dtoField;
    }

    public FieldAccessor getMainField() {
        return mainField;
    }

    public FieldAccessor getDtoField() {
        return dtoField;
    }
}
