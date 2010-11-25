package test;

import ru.frostman.jedto.DTOMapper;

/**
 * @author slukjanov aka Frostman
 */
public class Test {
    public static void main(String[] args) {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class);
    }
}
