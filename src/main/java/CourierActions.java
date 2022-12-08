import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierActions extends ScooterServiceData {

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Логин курьера")
    public ValidatableResponse login(CourierData credentials) {
        return given()
                .spec(getSettings())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSettings())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSettings())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();


    }
}
