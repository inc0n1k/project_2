package JSONEntity;

import entity.User;

public class UserJSON {
    private String name;
    private String login;

    public UserJSON(User user) {
        this.name = user.getName();
        this.login = user.getLogin();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
