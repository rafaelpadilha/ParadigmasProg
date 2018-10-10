package model;

import java.io.Serializable;

/**
 *
 * @author Rafael Padilha                 <github.com/rafaelpadilha>
 */
public class Login implements Serializable{
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login() {
    }

    public Login(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    
}
