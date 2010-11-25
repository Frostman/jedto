package ru.frostman.jedto.util;

import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class ClassConverter {
    private final Class mainClass;

    private final Class dtoClass;

    private final List<FieldConverter> fieldConverters;

    public ClassConverter(Class mainClass, Class dtoClass, List<FieldConverter> fieldConverters) {
        if(mainClass==null||dtoClass==null||fieldConverters==null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }

        this.mainClass = mainClass;
        this.dtoClass = dtoClass;
        this.fieldConverters = fieldConverters;
    }

    public Class getMainClass() {
        return mainClass;
    }

    public Class getDtoClass() {
        return dtoClass;
    }

    public List<FieldConverter> getFieldConverters() {
        return fieldConverters;
    }
}
