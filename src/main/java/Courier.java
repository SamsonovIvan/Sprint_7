public class Courier {
    private String login;
    private String password;
    private String name;


    public Courier(String login, String password, String name) {
        this.name = name;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return String.format("Курьер: логин - %s, пароль - %s, имя - %s", this.login, this.password, this.name);
    }
}
