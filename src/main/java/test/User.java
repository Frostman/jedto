package test;

import ru.frostman.jedto.annotations.MapDTO;

/**
 * @author slukjanov aka Frostman
 */
@MapDTO(UserDTO.class)
public class User {
    private String login;
    private String password;

    public User() {
    }

    public String getLogin() {
        return login;
    }

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
