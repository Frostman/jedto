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
