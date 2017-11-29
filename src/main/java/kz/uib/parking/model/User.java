package kz.uib.parking.model;

/**
 * @author Sanzhar Aubakirov (c0rp.aubakirov@gmail.com)
 */
public class User extends AbstractModel {

    String login;
    String password;
    String email;
    String nickname;
    Double balance; // количество денег
    Boolean isActivated;

    //... и т.д.

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString(){
        return this.getId() + ";" + login + ";" + password + ";" + ";" + email + ";" + nickname + ";" + balance;
    }

    public boolean checkPrerequisites(){
        if(this.login != null && this.email != null && this.nickname != null) {
            if(this.email.contains("@") && this.email.contains(".")){
                return true;
            }
            else return false;
        }
        else return false;
    }
}
