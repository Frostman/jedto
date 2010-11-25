package ru.frostman.jedto;

import net.sf.cglib.reflect.FastClass;
import ru.frostman.jedto.accessors.FieldAccessor;
import ru.frostman.jedto.accessors.FieldAccessorFactory;
import ru.frostman.jedto.annotations.MapDTO;
import ru.frostman.jedto.annotations.MapTo;
import ru.frostman.jedto.annotations.NotMap;
import ru.frostman.jedto.annotations.Transform;
import ru.frostman.jedto.transformation.Transformer;
import ru.frostman.jedto.util.ClassConverter;
import ru.frostman.jedto.util.FieldConverter;
import ru.frostman.jedto.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author slukjanov aka Frostman
 */
public class DTOMapper {
    private Set<String> mappedClassesSet = new LinkedHashSet<String>();
    private Set<String> dtoClassesSet = new LinkedHashSet<String>();
    private Map<String, String> mappedClasses = new LinkedHashMap<String, String>();

    private Map<String, ClassConverter> mappingByMainClass;
    private Map<String, ClassConverter> mappingByDTOClass;
    private Map<String, Transformer> transformers;

    private volatile boolean prepared;

    public DTOMapper() {
    }

    public synchronized void map(Class from) {
        prepared = false;

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

    //todo think about type-safe method
    //todo think about synchronization
    public Object toDTO(Object object) {
        if (object == null) {
            return null;
        }

        ClassConverter converter = mappingByMainClass.get(object.getClass().getName());

        if (converter == null) {
            //todo is it good?
            return object;
        }

        try {
            Object dto = converter.getDtoClass().newInstance();

            for (FieldConverter fieldConverter : converter.getFieldConverters()) {
                Object t = toDTO(fieldConverter.getMainField().getValue(object));
                if (fieldConverter.getTransformer() != null) {
                    //todo think about прямое трасформирование и обратное
                    t = fieldConverter.getTransformer().transformFirst(t);
                }
                fieldConverter.getDtoField().setValue(dto, t);
            }

            return dto;
        } catch (Exception e) {
            //todo impl
            throw new RuntimeException(e);
        }
    }

    //todo think about type-safe method
    //todo think about synchronization
    public Object fromDTO(Object dto) {
        if (dto == null) {
            return null;
        }

        ClassConverter converter = mappingByDTOClass.get(dto.getClass().getName());

        if (converter == null) {
            //todo is it good?
            return dto;
        }

        try {
            Object object = converter.getMainClass().newInstance();

            for (FieldConverter fieldConverter : converter.getFieldConverters()) {
                Object t = fromDTO(fieldConverter.getDtoField().getValue(dto));
                if (fieldConverter.getTransformer() != null) {
                    //todo think about прямое трасформирование и обратное
                    t = fieldConverter.getTransformer().transformSecond(t);
                }
                fieldConverter.getMainField().setValue(object, t);
            }

            return object;
        } catch (Exception e) {
            //todo impl
            throw new RuntimeException(e);
        }
    }

    public synchronized void prepare() {
        if (prepared) {
            return;
        }

        mappingByMainClass = new LinkedHashMap<String, ClassConverter>();
        mappingByDTOClass = new LinkedHashMap<String, ClassConverter>();
        transformers = new LinkedHashMap<String, Transformer>();

        try {
            for (Map.Entry<String, String> entry : mappedClasses.entrySet()) {
                Class from = Class.forName(entry.getKey());
                Class to = Class.forName(entry.getValue());

                ClassConverter classConverter = prepareClassPair(from, to);
                mappingByMainClass.put(from.getName(), classConverter);
                mappingByDTOClass.put(to.getName(), classConverter);
            }
        } catch (Exception e) {
            //todo it's not good
            throw new RuntimeException(e);
        }

        prepared = true;
    }

    private ClassConverter prepareClassPair(Class from, Class to) {
        List<Field> fromFields = ReflectionUtil.getDeclaredAndInheritedFields(from);
        List<Field> toFields = ReflectionUtil.getDeclaredAndInheritedFields(to);
        Map<String, Field> toFieldsMap = new LinkedHashMap<String, Field>();
        for (Field field : toFields) {
            toFieldsMap.put(field.getName(), field);
        }

        FastClass fcFrom = FastClass.create(from), fcTo = FastClass.create(to);
        List<FieldConverter> fieldConverters = new LinkedList<FieldConverter>();

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
            Transformer transformer = null;
            if (fromField.isAnnotationPresent(Transform.class)) {
                Transform transformAn = fromField.getAnnotation(Transform.class);
                Class<? extends Transformer> transformerClass = transformAn.value();

                transformer = transformers.get(transformerClass.getName());
                if (transformer == null) {
                    try {
                        transformer = transformerClass.newInstance();
                    } catch (Exception e) {
                        //is it good?
                        throw new RuntimeException(e);
                    }
                    transformers.put(transformerClass.getName(), transformer);
                }
            } else if (!fromField.getType().equals(toField.getType())
                    && (!mappedClasses.containsKey(fromField.getType().getName())
                    || !mappedClasses.get(fromField.getType().getName()).equals(toField.getType().getName()))) {
                throw new IllegalArgumentException("Can't transform " + fromField.getType() + " to " + toField.getType());
            }

            FieldAccessor fromFieldAccessor = FieldAccessorFactory.create(fromField, fcFrom);
            FieldAccessor toFieldAccessor = FieldAccessorFactory.create(toField, fcTo);

            FieldConverter fieldConverter = new FieldConverter(fromFieldAccessor, toFieldAccessor, transformer);
            fieldConverters.add(fieldConverter);

            //todo log it to debug or trace
            System.out.println(fromField + " -> " + toField);
        }

        return new ClassConverter(from, to, fieldConverters);
    }
}
