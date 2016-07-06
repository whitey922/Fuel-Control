package Auto;

/**
 * Created by Yegorov on 29.05.2016.
 */
public class User {
    public String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String login;
    private String password;

    public User(String ID,  String login, String password) {
        this.password = password;
        this.login = login;
        this.ID = ID;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
