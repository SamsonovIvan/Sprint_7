import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCourierTest {
    private Courier courier;
    private CourierActions courierActions;
    private int courierId;

    @Before
    public void setUp(){
        courier = DataGenerator.getRandomCourier();
        courierActions = new CourierActions();
        courierActions.create(courier);
    }
    @After
    public void tearDown(){
        courierActions.delete(courierId);
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void courierCanLiginTest(){
        ValidatableResponse response = courierActions.login(CourierData.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        courierId = response.extract().path("id");
        assertNotEquals(0,courierId);
    }

    @Test
    @DisplayName("Попытка авторизации без ввода логина")
    @Description("endpoint /api/v1/courier/login")
    public void loginWithEmptyLogin(){
        ValidatableResponse response = courierActions.login(new CourierData("", courier.getPassword()));
        courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        String answerBody = response.extract().path("message");
        assertEquals("Недостаточно данных для входа", answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации без ввода пароля")
    @Description("endpoint /api/v1/courier/login")
    public void loginWithEmptyPassword(){
        ValidatableResponse response = courierActions.login(new CourierData(courier.getLogin(), ""));
        courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        String answerBody = response.extract().path("message");
        assertEquals("Недостаточно данных для входа", answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации с логином - null")
    @Description("endpoint /api/v1/courier/login")
    public void nullLogin(){
        ValidatableResponse response = courierActions.login(new CourierData(null, courier.getPassword()));
        courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
        String answerBody = response.extract().path("message");
        assertEquals("Недостаточно данных для входа", answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации с паролем - null")
    @Description("endpoint /api/v1/courier/login")
    public void nullPassword(){
        ValidatableResponse response = courierActions.login(new CourierData(courier.getLogin(), null));
        courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode); //Возможен SC - 503
        String answerBody = response.extract().path("message");
        assertEquals("Недостаточно данных для входа", answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации несуществующим пользователем")
    @Description("endpoint /api/v1/courier/login")
    public void userNotExist(){
        ValidatableResponse response = courierActions.login(new CourierData("qwerty", "12345678"));
        int statusCode = response.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
        String answerBody = response.extract().path("message");
        assertNotNull(answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации с неверным логином")
    @Description("endpoint /api/v1/courier/login")
    public void incorrectLogin(){
    ValidatableResponse response = courierActions.login(new CourierData("asdf", courier.getPassword()));
    courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
    int statusCode = response.extract().statusCode();
    assertEquals(SC_NOT_FOUND, statusCode);
    String answerBody = response.extract().path("message");
    assertEquals("Учетная запись не найдена", answerBody);
    }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем")
    @Description("endpoint /api/v1/courier/login")
    public void incorrectPassword(){
        ValidatableResponse response = courierActions.login(new CourierData(courier.getLogin(), "12345678"));
        courierId = courierActions.login(CourierData.from(courier)).extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
        String answerBody = response.extract().path("message");
        assertEquals("Учетная запись не найдена", answerBody);
    }
}
