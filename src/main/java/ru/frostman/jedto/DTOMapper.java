package ru.frostman.jedto;

import net.sf.cglib.reflect.FastClass;
import ru.frostman.jedto.accessors.FieldAccessor;
import ru.frostman.jedto.accessors.FieldAccessorFactory;
import ru.frostman.jedto.annotations.MapDTO;
import ru.frostman.jedto.annotations.MapTo;
import ru.frostman.jedto.annotations.NotMap;
import ru.frostman.jedto.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author slukjanov aka Frostman
 */
public class DTOMapper {
    public static final String GENERATE_VALUE = "";

    private Set<String> mappedClassesSet = new LinkedHashSet<String>();
    private Set<String> dtoClassesSet = new LinkedHashSet<String>();

    private Map<String, String> mappedClasses = new LinkedHashMap<String, String>();

    private volatile boolean ready;

    public DTOMapper() {
    }

    public synchronized void map(Class from) {
        ready = false;

        if (from == null) {
            throw new IllegalArgumentException("Mapped class can't be null");
        }

        if (!from.isAnnotationPresent(MapDTO.class)) {
            throw new IllegalArgumentException("Mapped class must be annotated with @MapDTO");
        }

        @SuppressWarnings({"unchecked"})
        MapDTO mapDTOAn = (MapDTO) from.getAnnotation(MapDTO.class);
        Class to = mapDTOAn.value();

        if (to == null) {
            throw new IllegalArgumentException("MapTo class can't be null");
        }

        if (mappedClassesSet.contains(from.getName())) {
            throw new IllegalArgumentException("Class \"" + from.getName() + "\" is already mapped");
        }

        if (dtoClassesSet.contains(from.getName())) {
            throw new IllegalArgumentException("Class \"" + from.getName() + "\" is already mapped as dto");
        }

        if (mappedClassesSet.contains(to.getName())) {
            throw new IllegalArgumentException("Class \"" + from.getName() + "\" is already mapped");
        }

        if (dtoClassesSet.contains(to.getName())) {
            throw new IllegalArgumentException("Class \"" + from.getName() + "\" is already mapped as dto");
        }

        mappedClassesSet.add(from.getName());
        dtoClassesSet.add(to.getName());
        mappedClasses.put(from.getName(), to.getName());
    }

    public synchronized void prepare() {
        if (ready) {
            return;
        }

        try {
            for (Map.Entry<String, String> entry : mappedClasses.entrySet()) {
                Class from = Class.forName(entry.getKey());
                Class to = Class.forName(entry.getValue());

                prepareClassPair(from, to);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ready = true;
    }

    private void prepareClassPair(Class from, Class to) {
        List<Field> fromFields = ReflectionUtil.getDeclaredAndInheritedFields(from);
        List<Field> toFields = ReflectionUtil.getDeclaredAndInheritedFields(to);
        Map<String, Field> toFieldsMap = new LinkedHashMap<String, Field>();
        for (Field field : toFields) {
            toFieldsMap.put(field.getName(), field);
        }

        FastClass fcFrom = FastClass.create(from), fcTo = FastClass.create(to);

        for (Field fromField : fromFields) {
            if (fromField.isAnnotationPresent(NotMap.class)) {
                continue;
            }

            String toFieldName = fromField.getName();
            if (fromField.isAnnotationPresent(MapTo.class)) {
                MapTo mapToAn = fromField.getAnnotation(MapTo.class);
                toFieldName = mapToAn.value();
            }

            if (!toFieldsMap.containsKey(toFieldName)) {
                //todo another exception
                throw new IllegalArgumentException("Class \"" + to.getName() +
                        "\" isn't contains field \"" + toFieldName + "\"");
            }

            Field toField = toFieldsMap.get(toFieldName);

            FieldAccessor fromFieldAccessor = FieldAccessorFactory.create(fromField, fcFrom);
            FieldAccessor toFieldAccessor = FieldAccessorFactory.create(toField, fcTo);



            //todo log it to debug or trace
            System.out.println(fromField + " -> " + toField);
        }
    }
}
