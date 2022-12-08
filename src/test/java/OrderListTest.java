import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderActions orderActions;
    @Before
    public void setUp(){
        orderActions = new OrderActions();
    }
    @Test
    @DisplayName("Список заказов не пустой")
    @Description("endpoint api/v1/orders")
    public void listOfOrdersIsNotEmpty(){
        ValidatableResponse response = orderActions.getDefaultOrder();
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        List<String> answerBody = response.extract().path("orders");
        assertFalse(answerBody.isEmpty());
    }
    @Test
    @DisplayName("Список заказов не равен null")
    @Description("endpoint api/v1/orders")
    public void listOfOrdersNotNull(){
        ValidatableResponse response = orderActions.getDefaultOrder();
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
        List<String> answerBody = response.extract().path("orders");
        assertNotEquals(null, answerBody);
    }
}
