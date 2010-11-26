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

        //todo log if can't access getter or setter
        try {
            getter = fastClass.getMethod(ReflectionUtil.getFieldGetterName(field), new Class[]{});
        } catch (Throwable e) {
            //no operation
        }

        try {
            setter = fastClass.getMethod(ReflectionUtil.getFieldSetterName(field), new Class[]{field.getType()});
        } catch (Throwable e) {
            //no operation
        }
    }

    public Object getValue(Object instance) {
        try {
            if (getter != null) {
                return getter.invoke(instance, new Object[]{});
            }

            return field.get(instance);
        } catch (Exception e) {
            //todo is it good?
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object instance, Object newValue) {
        try {
            if (setter != null) {
                setter.invoke(instance, new Object[]{newValue});
                return;
            }

            field.set(instance, newValue);
        } catch (Exception e) {
            //todo impl is is good?
            throw new RuntimeException(e);
        }
    }

    public Class getType() {
        return field.getType();
    }
}
