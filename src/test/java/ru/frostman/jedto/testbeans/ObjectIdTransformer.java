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

package ru.frostman.jedto.testbeans;

import ru.frostman.jedto.transformation.Transformer;

/**
 * @author slukjanov aka Frostman
 */
public class ObjectIdTransformer implements Transformer {
    public Object transformDirect(Object objectId) {
        if (objectId == null || !(objectId instanceof ObjectId)) {
            return null;
        }

        return ((ObjectId) objectId).getOid();
    }

    public Object transformInverse(Object s) {
        if (s == null || !(s instanceof String)) {
            return null;
        }
        return new ObjectId((String) s);
    }
}
