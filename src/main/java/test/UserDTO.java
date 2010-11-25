package test;

/**
 * @author slukjanov aka Frostman
 */
public class UserDTO {
    private String login;
    private String pswd;

    private UserDTO user;

    public UserDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
