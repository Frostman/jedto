package ru.frostman.jedto.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class ReflectionUtil {

    public static List<Field> getDeclaredAndInheritedFields(final Class type) {
        return getDeclaredAndInheritedFields(type, null);
    }

    private static List<Field> getDeclaredAndInheritedFields(final Class type, List<Field> fields) {
        if ((type == null) || (type == Object.class)) {
            return fields;
        }
        if (fields == null) {
            fields = new LinkedList<Field>();
        }

        Class superClass = type.getSuperclass();
        fields = getDeclaredAndInheritedFields(superClass, fields);

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            int fieldModifiers = field.getModifiers();
            if (Modifier.isFinal(fieldModifiers)) {
                continue;
            }

            fields.add(field);
        }

        return fields;
    }

    public static String getFieldGetterName(Field field) {
        boolean isBooleanPrimitive = field.getType() == boolean.class;

        String fieldName = field.getName();
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return isBooleanPrimitive ? "is" : "get" + fieldName;
    }

    public static String getFieldSetterName(Field field) {
        String fieldName = field.getName();
        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return "set" + fieldName;
    }
}
