package ru.frostman.jedto;

import ru.frostman.jedto.annotations.MapDTO;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class DTOMapper {

    public DTOMapper() {
    }

    public void map(Class from) {
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

        List<Field> fields = ReflectionUtil.getDeclaredAndInheritedFields(from);


    }
}
