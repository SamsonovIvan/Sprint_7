import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
public class ScooterServiceData {
    public static final String URL = "https://qa-scooter.praktikum-services.ru";
   protected RequestSpecification getSettings(){
       return new RequestSpecBuilder()
               .setBaseUri(URL)
               .setContentType(ContentType.JSON)
               .build();
   }
}
