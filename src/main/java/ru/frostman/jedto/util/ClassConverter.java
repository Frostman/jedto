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
