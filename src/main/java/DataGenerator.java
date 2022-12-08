import com.github.javafaker.Faker;
public class DataGenerator {
    static Faker faker = new Faker();

    public static Courier getRandomCourier() {
        String name = faker.name().username();
        String login = faker.name().fullName();
        String password = String.valueOf(faker.code());
        return new Courier(login, password, name);
    }

    public static Order getRandomOrderDataWithoutColor(String[] color){
        String firstName = faker.name().firstName();
        String lastname = faker.name().lastName();
        String adress = "Санкт-Петербург";
        String metroStation = faker.number().digit();
        String phone = "+70000000000";
        int rentTime = 3;
        String deliveryDate = "2023-03-28";
        String comment = faker.backToTheFuture().quote();
        return new Order(firstName, lastname, adress, metroStation, phone, rentTime,deliveryDate,comment,color);
    }
}

