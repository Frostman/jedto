package ru.frostman.jedto;

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
            int fieldModifiers = field.getModifiers();
            if (Modifier.isFinal(fieldModifiers)) {
                continue;
            }

            fields.add(field);
        }

        return fields;
    }

}
