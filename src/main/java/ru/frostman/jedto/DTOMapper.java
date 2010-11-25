package ru.frostman.jedto;

import ru.frostman.jedto.annotations.MapDTO;

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

        for(Field field:fromFields) {

        }
    }
}
