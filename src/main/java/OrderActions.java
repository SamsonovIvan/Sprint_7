import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class OrderActions extends ScooterServiceData {
    private static final String ORDER_URL = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order){
        return given()
                .spec(getSettings())
                .body(order)
                .when()
                .post(ORDER_URL)
                .then();
    }
    @Step("Получение списка заказов")
    public ValidatableResponse getDefaultOrder() {
        return given()
                .spec(getSettings())
                .when()
                .get(ORDER_URL)
                .then();
    }


}
