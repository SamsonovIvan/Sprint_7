import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierActions courierActions;
    private int courierId;


    @Before
    public void setUp() {
        courierActions = new CourierActions();
    }

    @After
    public void tearDown() {
        courierActions.delete(courierId);
    }

    @Test
    @DisplayName("Проверка создания курьера (подходящие данные)")
    @Description("endpoint /api/v1/courier")
    public void courierCreatedTest() {
        courier = DataGenerator.getRandomCourier();
        ValidatableResponse response = courierActions.create(courier);
        ValidatableResponse loginResponse = courierActions.login(CourierData.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, loginStatusCode);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue(isCreated);
        courierId = loginResponse.extract().path("id");
        assertNotEquals(0, courierId);
    }
    @Test
    @DisplayName("Создание двух одинаковых курьеров (ожидаем ошибку)")
    @Description("endpoint /api/v1/courier")
    public void doppelhangerCourierTest(){
        Courier courier1 = new Courier(DataGenerator.getRandomCourier().getLogin(), DataGenerator.getRandomCourier().getPassword(), DataGenerator.getRandomCourier().getName());
        courierActions.create(courier1);
        ValidatableResponse response = courierActions.create(courier1);
        ValidatableResponse loginResponse = courierActions.login(CourierData.from(courier1));
        courierId = loginResponse.extract().path("id");
        String answer = response.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", answer);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }
    @Test
    @DisplayName("Создание курьера с именем - null")
    @Description("endpoint /api/v1/courier")
    public void courierNullNameTest(){
        Courier courier1 = new Courier(DataGenerator.getRandomCourier().getLogin(), DataGenerator.getRandomCourier().getPassword(), null);
        ValidatableResponse response = courierActions.create(courier1);
        courierId = courierActions.login(CourierData.from(courier1)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue(isCreated);
    }
    @Test
    @DisplayName("Создание курьера с паролем - null")
    @Description("endpoint /api/v1/courier")
    public void courierNullPasswordTest(){
        Courier courier1 = new Courier(DataGenerator.getRandomCourier().getLogin(), null, DataGenerator.getRandomCourier().getName());
        ValidatableResponse response = courierActions.create(courier1);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

        String answer = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", answer);
    }
    @Test
    @DisplayName("Создание курьера без логина")
    @Description("endpoint /api/v1/courier")
    public void courierEmptyLoginTest(){
        Courier courier1 = new Courier("", DataGenerator.getRandomCourier().getPassword(), DataGenerator.getRandomCourier().getName());
        ValidatableResponse response = courierActions.create(courier1);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        String answer = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", answer);
    }
    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("endpoint /api/v1/courier")
    public void courierEmptyPasswordTest(){
        Courier courier1 = new Courier(DataGenerator.getRandomCourier().getLogin(), "", DataGenerator.getRandomCourier().getName());
        ValidatableResponse response = courierActions.create(courier1);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST,statusCode);
        String answer = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", answer);
    }

}