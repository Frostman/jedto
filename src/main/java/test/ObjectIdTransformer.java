package test;

import ru.frostman.jedto.transformation.Transformer;

/**
 * @author slukjanov aka Frostman
 */
public class ObjectIdTransformer implements Transformer<ObjectId, String> {
    public String transformFirst(ObjectId objectId) {
        if (objectId == null) {
            return null;
        }
        return objectId.getOid();
    }

    public ObjectId transformSecond(String s) {
        if (s == null) {
            return null;
        }
        return new ObjectId(s);
    }
}
