package test;

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
