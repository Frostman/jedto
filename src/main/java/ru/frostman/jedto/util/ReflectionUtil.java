/*
 * Copyright 2010 Sergey "Frostman" Lukjanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
