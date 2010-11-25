package test;

import ru.frostman.jedto.annotations.MapDTO;
import ru.frostman.jedto.annotations.MapTo;
import ru.frostman.jedto.annotations.Transform;

/**
 * @author slukjanov aka Frostman
 */
@MapDTO(UserDTO.class)
public class User {
    @Transform(ObjectIdTransformer.class)
    private ObjectId id;

    private String login;

    @MapTo("pswd")
    private String password;

    private User user;

    public User() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
