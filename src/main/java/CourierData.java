public class CourierData {
    private final String password;
    private final String login;

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierData from(Courier courier) {
        return new CourierData(courier.getLogin(), courier.getPassword());
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return String.format("Курьер: логин - %s, пароль - %s", this.login, this.password);
    }
}