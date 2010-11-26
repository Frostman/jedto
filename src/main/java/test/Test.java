package test;

import ru.frostman.jedto.DTOMapper;

/**
 * @author slukjanov aka Frostman
 */
public class Test {
    public static void main(String[] args) {
        DTOMapper dtoMapper = new DTOMapper();
        dtoMapper.map(User.class);

        dtoMapper.prepare();

        User user =  new User();
        user.setLogin("logggin");
        user.setPassword("passwordddd");
        user.setId(new ObjectId("objId"));
        User u2 = new User();
        u2.setLogin("uuuuu2");
        u2.setPassword("ppppp2");
        user.setUser(u2);
        u2.setUser(user);

        UserDTO userDTO = (UserDTO) dtoMapper.toDTO(user);
        System.out.println(userDTO);

        User ttt = (User) dtoMapper.fromDTO(userDTO);
        System.out.println(ttt);

    }
}
