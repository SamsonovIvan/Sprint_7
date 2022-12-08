import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;
    private OrderActions orderActions;
    private final String[] color;
    public CreateOrderTest(String color){
        this.color = new String[]{color};
    }
    @Before
    public void setUp(){
        orderActions = new OrderActions();
    }
    @Parameterized.Parameters
    public static Object[][] setColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {""},
                {"BLACK , GREY"}
        };
    }
    @Test
    @DisplayName("Проверка создания заказа с изменением цвета")
    @Description("endpoint /api/v1/orders")
    public void createOrderTest(){
        order = DataGenerator.getRandomOrderDataWithoutColor(color);
        ValidatableResponse response = orderActions.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        int trackNumber = response.extract().path("track");
        assertNotEquals(0, trackNumber);
    }

}
