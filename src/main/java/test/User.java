package test;

import ru.frostman.jedto.annotations.MapDTO;
import ru.frostman.jedto.annotations.MapTo;
import ru.frostman.jedto.annotations.NotMap;

/**
 * @author slukjanov aka Frostman
 */
@MapDTO(UserDTO.class)
public class User {
    @NotMap
    private String id;

    private String login;

    @MapTo("pswd")
    private String password;

    private User user;

    public User() {
    }

//    public String getLogin() {
//        return login;
//    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
